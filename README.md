# analytics-service
Cервис для хранения шаблонов сообщений и отправки сообщений по запросу. 

Предоставляется 7 эндпоинтов:

* /template - загрузка шаблона
  Пример шаблона:
```
{
"templateId": "Report",
"template": "The overall score was $mark$, the date: $date$",
"recipients": ["https://httpbin.org/post", "https://postman-echo.com/post"],
"varTypes": {"mark": "double", "date": "date"}
}
```
* 
