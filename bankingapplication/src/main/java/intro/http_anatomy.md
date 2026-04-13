# Anatomy of an HTTP Request

## Overview

An HTTP request is a structured message sent by a client (such as a web browser, mobile application, or other software) to a server to initiate a specific action or retrieve data. This communication channel is established to execute a particular operation or obtain information.

---

## Raw Request Example

```http
GET https://www.server.com/api/orders.json?created_at=2022-01-18T19:08:48+00:00&limit=10 HTTP/1.1
Host: www.server.com
User-Agent: Mozilla/5.0
Accept: application/json
Authorization: Basic Yjw2WU57IogGNiZzgxODhjZwBkOWIwZjdjZTY=
Connection: keep-alive

```

---

## Core Components of an HTTP Request

### REQUEST LINE

The request line is the first line of an HTTP message and consists of three parts:

```
METHOD RESOURCE HTTP-VERSION
```

**Example:**
```
GET /api/orders.json?created_at=2022-01-18T19:08:48+00:00&limit=10 HTTP/1.1
```

#### **HTTP Methods (Verbs)**

| Method | Purpose | Description |
|--------|---------|-------------|
| **GET** | Retrieve Data | Used to request data from the server. No request body. |
| **POST** | Submit Data | Used to create new data. Contains data in the request body. |
| **PUT** | Full Update | Replaces the entire resource. |
| **PATCH** | Partial Update | Updates specific fields of a resource. |
| **DELETE** | Remove Data | Used to delete data from the server. |
| **HEAD** | Metadata | Same as GET but without response body. |
| **OPTIONS** | Check Options | Used to query allowed methods supported by the server. |

---

### URL (Uniform Resource Locator)

The complete web address that identifies a specific resource, composed of several distinct parts.

#### **URL Structure**

```
https://www.server.com:443/api/orders.json?created_at=2022-01-18&limit=10
└─┬──┘ └──────┬─────────┘ └─┬──┘ └────────┬──────────┘ └─────────┬─────────┘
  │            │             │             │               │
SCHEME       SERVER        PORT          PATH         QUERY STRING
```

#### **URL Components**

| Component | Description | Example |
|-----------|-------------|---------|
| **SCHEME** | Communication protocol | `https://` (Secure HTTP) or `http://` |
| **SERVER (Host)** | Domain name or IP address | `www.server.com` |
| **PORT** | Port number for the request (optional) | `:443` (HTTPS default), `:80` (HTTP) |
| **PATH** | Resource location on the server | `/api/orders.json` |
| **QUERY STRING** | Filtering and parameters | `?key1=value1&key2=value2` |
| **FRAGMENT** | Specific section of the page (optional) | `#section1` |

#### **Query String Characteristics**

- Begins with a question mark `?`
- Multiple parameters are separated by an ampersand `&`
- Uses key=value format
- URL encoding should be applied (e.g., space = `%20`)

**Example:**
```
?created_at=2022-01-18T19:08:48+00:00&limit=10&sort=desc
```

---

### HEADERS

Headers are key-value pairs that provide essential metadata and context information about the request to the server.

#### **Common HTTP Headers**

| Header | Purpose | Example |
|--------|---------|---------|
| **Host** (Required) | Target server domain name | `Host: www.server.com` |
| **User-Agent** | Information about client software | `User-Agent: Mozilla/5.0 (Windows NT 10.0)` |
| **Accept** | Accepted response format | `Accept: application/json` |
| **Accept-Language** | Preferred language | `Accept-Language: en-US, tr-TR` |
| **Content-Type** | Format of sent data (POST/PUT) | `Content-Type: application/json` |
| **Content-Length** | Size of body data in bytes | `Content-Length: 256` |
| **Authorization** | Authentication credentials | `Authorization: Bearer token123` |
| **Connection** | Connection type | `Connection: keep-alive` |
| **Cache-Control** | Cache control settings | `Cache-Control: no-cache` |
| **Referer** | Page that initiated the request | `Referer: https://www.example.com` |
| **Cookie** | Stored session information | `Cookie: session=abc123; user=john` |

---

### REQUEST BODY

Some HTTP methods (POST, PUT, PATCH) can include a request body.

#### **Examples:**

**JSON Format:**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "account_type": "savings"
}
```

**Form Data:**
```
username=john&password=secret123&remember=true
```

**XML Format:**
```xml
<customer>
  <name>John Doe</name>
  <email>john@example.com</email>
</customer>
```

---

## Complete HTTP Request Example

```http
POST https://api.bank.com/api/customers HTTP/1.1
Host: api.bank.com
Content-Type: application/json
Content-Length: 65
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
Accept: application/json
User-Agent: BankingApp/1.0

{"name":"John Doe","email":"john@example.com","type":"savings"}
```

---

## Authentication (Authentication) Methods

| Method | Description | Example |
|--------|-------------|---------|
| **Basic Auth** | Base64 encoded username:password | `Authorization: Basic dXNlcjpwYXNz` |
| **Bearer Token** | JWT or OAuth token | `Authorization: Bearer eyJhbGc...` |
| **API Key** | Special authentication key | `Authorization: ApiKey abc123xyz` |
| **OAuth 2.0** | Third-party authorization | `Authorization: Bearer access_token` |

---

## Best Practices

**Do's:**
- Send sensitive data over `HTTPS`
- Set the `Content-Type` header correctly
- Use secure authentication methods
- Apply URL encoding to query parameters
- Validate and sanitize all user inputs
- Use appropriate HTTP methods for operations

**Don'ts:**
- Do not send passwords over HTTP
- Do not include sensitive data in query strings
- Do not use invalid HTTP methods
- Do not omit the Host header
- Do not send unencrypted credentials
- Do not ignore error responses