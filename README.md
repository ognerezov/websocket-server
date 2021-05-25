# Getting Started

## Message types

### Messages on someone enters or exits a room
{"type":"ParticipantEnter","data":{"id":"148","participants":[{"name":"Иван Иванович","room":"148","admin":false,"authorities":[],"id":"be522d2f-2493-e92b-3f1b-1ab8f479b797"}]}}

{"type":"ParticipantExit","data":{"id":"148","participants":[{"name":"Easy","room":"148","admin":true,"authorities":[],"id":"904f1a18-0dd7-172f-f7c9-e637a68775ba"}]}}

data is full room description

### Broadcast
{"type": "Broadcast","data": {"some":"data"}}

data - any String,Object field set

The message will be broadcast to all room participants but sender

Sender will get following response 

{"type":"ServerResponse","data":{"value":"ok","details":"sent"}}

If a sender doesn't have broadcast, permission he will receive:
{"type":"ServerResponse","data":{"value":"fail","details":"forbidden"}}

### Direct

{"type":"Direct","participants":["e12a61e9-d80e-6e50-0882-17d64768a782"],"data":{"some":"data"}}

Message will be redirected to all recipients

list of participants is mandatory. You'll get following message without the list

{"type":"ServerResponse","data":{"value":"fail","details":"participants are missing"}}

If a sender doesn't have Direct permission, he will receive:
{"type":"ServerResponse","data":{"value":"fail","details":"forbidden"}}

### Control

{"type":"Control","participants":["81375143-a238-273c-f976-3a10e9a26b6e"],"data":{"allow":true,"authorities":["Broadcast"]}}

list of participants is mandatory. You'll get following message without the list

{"type":"ServerResponse","data":{"value":"fail","details":"participants are missing"}}

If a sender doesn't have Control permission, he will receive:
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

### Petition

{"type":"Petition","data":{"some":"data"}}

Message will be redirected to all participants with Control permission and to room admin

This message type doesn't require any permissions or participant list

### Disconnect
{"type": "Disconnect"}

Client should send it on user exit. No answer. No other requirements

### Kick

{"type":"Kick","participants":["9246c673-7127-2cdd-50f4-acaf26a5ae64"]}

Disconnects a group of users from the room. Kick permission and participants list are required

### Close

{"type":"Close"}

Disconnect all participants and closes a room. Sender must have Close permission



