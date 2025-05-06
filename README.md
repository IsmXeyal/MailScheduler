### 📨 Send Email API

**Endpoint:**  
`POST /api/emails/sendEmail`

**Request Body:**
```json
{
  "toEmail": "your-email@gmail.com",
  "subject": "Subject",
  "body": "Hello, this is test"
}
Description:
This request will store the email in the email_outbox with status PENDING. It will be picked up and sent during the next scheduled email processing run.
