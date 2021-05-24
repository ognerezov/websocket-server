# Getting Started

## Message types

### Broadcast
{"type": "Broadcast","data": {"id":"eyJhbGciOiJIUzUxMiJ9"}}

data - any String,Object field set

The message will be broadcast to all room participants but sender

Sender will get following response 

{"type":"ServerResponse","data":{"value":"ok","details":"sent"}}

If a sender doesn't have broadcast, permission he will receive:
{"type":"ServerResponse","data":{"value":"fail","details":"forbidden"}}

### Control

{"type":"Control","participants":["81375143-a238-273c-f976-3a10e9a26b6e"],"data":{"allow":true,"authorities":["Broadcast"]}}

list of participants is mandatory. You'll get following message without th list

{"type":"ServerResponse","data":{"value":"fail","details":"participants are missing"}}

If a sender doesn't have control, permission he will receive:
{"type":"ServerResponse","data":{"value":"fail","details":"forbidden"}}

If applying control was successful, every participant will receive a message with new room data:

{
"type": "RolesApplied",
"data": {
"id": "148",
"participants": [
{
"name": "Student Name",
"room": "148",
"admin": false,
"authorities": [
"Broadcast"
],
"id": "4abe71fc-88cf-d087-4fb3-340fbf38d654"
},
{
"name": "Easy",
"room": "148",
"admin": true,
"authorities": [],
"id": "935549a3-5aaa-8c9d-76d7-17eb76176d89"
},
{
"name": "kipokjhh",
"room": "148",
"admin": false,
"authorities": [],
"id": "f08b5b6a-ed7d-2662-e49e-5f8fda361134"
}
]
}
}





