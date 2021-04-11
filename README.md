# analytics-service
Cервис для хранения шаблонов сообщений и отправки сообщений по запросу. 

### Предоставляется 7 эндпоинтов:

* /template/load - загрузка шаблона
  Пример тела запроса:
```
{
  "templateId": "Report",
  "template": "The overall score was $mark$, the date: $date$",
  "recipients": ["https://httpbin.org/post", "https://postman-echo.com/post"],
  "varTypes": {"mark": "double", "date": "date"}
}
```
  varTypes - для валидации типов ("int", "string", "date", "time", "double"). Те переменные для которых тип не указан не валидируются. Может не быть подан совсем.
  Имена переменных (в примере $mark$ и $date$) могут содержать escape-символы (Пример: $$$, $^.$).
  При запросе с шаблоном существующего templateId данные обновляются
* /template/send отправка сообщений
  Пример тела запроса:
```
{
  "templateId": "Report",
  "variables": {"mark": "5.6", "date": "03/05/2021"}
}
```
  Если шаблон не содержит получателей или тип переменных не соответсвует код ответа - 400
* /template/get?templateId={templateId}
  Получение информации о шаблоне по templateId
  Пело ответа соответсвует телу запроса при /template/load 
  Если такого шаблона не существует код ответа - 400
* /template/{templateId}/addRecipient?url={url}
  По id шаблона добавляет нового получателя. Если шаблон уже содержит такого получателя или такого шаблона нет код ответа - 400 
* /template/{templateId}/deleteRecipient?url={url}
  По id шаблона удаляет получателя. Если такого нет код ответа - 400
* /template/subscribe?time={time}
  Тело запроса соответсвует телу запроса при отправке сообщения /template/send
  Запрос на отправку сообщений соответсвующего шаблона с заданными параметрами раз в time миллисекунд
  Возвращет объект:
```
{
  "message": "The meeting will take place in the Room No.555555 at 111111111 o'clock",
    "urls": [
      "https://httpbin.org/post",
      "https://postman-echo.com/post"
    ]
}
```
* /template/unsubscribe
  Тело запроса соответсвует телу ответа при подписке /template/subscribe?time={time}
  И соответсвующая подписка удаляется. Сообщения перестают отправляться.
