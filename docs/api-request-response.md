**Generate Numbers**

Generating a sequence of numbers in the decreasing order till 0. 

[HTTP POST] http://localhost:9091/api/generate

Request:

```
{
    "goal": 100,
    "step": 3
}
```

Response:

Status: 202 Accepted
```
{
    "task": "5fd9e8bd4fb89b245798d2db"
}
```

**Get Task**

Get the task created to generate the Numbers.

[HTTP GET] http://localhost:9091/api/tasks/5fd9e8bd4fb89b245798d2db

Response:

```
{
    "@id": 1,
    "id": "5fd9e8bd4fb89b245798d2db",
    "name": "NumberGenerator",
    "goal": 100,
    "step": 3,
    "status": "SUCCESS",
    "goalStepsMap": null,
    "result": [
        100, 97, 94, 91, 88, 85, 82, 79, 76, 73,
        70, 67, 64, 61, 58, 55, 52, 49, 46, 43,
        40, 37, 34, 31, 28, 25, 22, 19, 16, 13,
        10, 7, 4, 1, 0
    ],
    "results": null,
    "bulkTask": false
}
```

**Get Task status**

Get the task status created to generate the Numbers.
[HTTP GET] http://localhost:9091/api/tasks/5fd9e8bd4fb89b245798d2db/status

Response:

```
{
    "status": "SUCCESS"
}
```

**Get Task Result i.e. Number List**

Get the task result i.e. generated numbers.
[HTTP GET] http://localhost:9091/api/tasks/5fd9e8bd4fb89b245798d2db?action=get_numlist

Response:

```
{
    "result": [
            100, 97, 94, 91, 88, 85, 82, 79, 76, 73,
            70, 67, 64, 61, 58, 55, 52, 49, 46, 43,
            40, 37, 34, 31, 28, 25, 22, 19, 16, 13,
            10, 7, 4, 1, 0
    ]
}
```

**Bulk Generate Numbers**

Generating a sequence of numbers in the decreasing order till 0 for the given inputs. 

[HTTP POST] http://localhost:9091/api/bulkGenerate

Request:

```
{
    "tasks": [
        {
            "goal": 100,
            "step": 3
        },
        {
            "goal": 10,
            "step": 2
        }
    ]
}
```

Response:

Status: 202 Accepted
```
{
    "task": "5fd9e8d54fb89b245798d2dc"
}
```

**Get Task Results i.e. Number Lists**

Get the task results i.e. generated number list.

[HTTP GET] http://localhost:9091/api/tasks/5fd9e8bd4fb89b245798d2db?action=get_numlist

Response:

```
{
    "results": [
             [
             100, 97, 94, 91, 88, 85, 82, 79, 76, 73,
             70, 67, 64, 61, 58, 55, 52, 49, 46, 43,
             40, 37, 34, 31, 28, 25, 22, 19, 16, 13,
             10, 7, 4, 1, 0
             ],
             [
             10, 8, 6, 4, 2, 0
             ]
    ]
}
```

**List Tasks**

Get the list of tasks created to generate the Numbers.

[HTTP GET] http://localhost:9091/api/tasks

Response:

```
[
    {
        "@id": 1,
        "id": "5fd9e8bd4fb89b245798d2db",
        "name": "NumberGenerator",
        "goal": 100,
        "step": 3,
        "status": "SUCCESS",
        "goalStepsMap": null,
        "result": [
                  100, 97, 94, 91, 88, 85, 82, 79, 76, 73,
                  70, 67, 64, 61, 58, 55, 52, 49, 46, 43,
                  40, 37, 34, 31, 28, 25, 22, 19, 16, 13,
                  10, 7, 4, 1, 0
        ],
        "results": null,
        "bulkTask": false
    },
    {
        "@id": 2,
        "id": "5fd9e8d54fb89b245798d2dc",
        "name": "BulkNumberGenerator",
        "goal": 0,
        "step": 0,
        "status": "SUCCESS",
        "goalStepsMap": {
            "100": 3,
            "10": 2
        },
        "result": null,
        "results": [
                 [
                 100, 97, 94, 91, 88, 85, 82, 79, 76, 73,
                 70, 67, 64, 61, 58, 55, 52, 49, 46, 43,
                 40, 37, 34, 31, 28, 25, 22, 19, 16, 13,
                 10, 7, 4, 1, 0
                 ],
                 [
                 10, 8, 6, 4, 2, 0
                 ]
        ],
        "bulkTask": true
    }
]
```

**PUT Task**

Update the task to generate new sequence of numbers. 

[HTTP PUT] http://localhost:9091/api/tasks/5fd9e8bd4fb89b245798d2db

Request:

```
{
    "goal": 100,
    "step": 10
}
```

Response:

Status: 202 Accepted
```
{
    "task": "5fd9e8bd4fb89b245798d2db"
}
```

**Get Updated Task**

Get the task updated to generate the new Numbers.

[HTTP GET] http://localhost:9091/api/tasks/5fd9e8bd4fb89b245798d2db

Response:

```
{
    "@id": 1,
    "id": "5fd9e8bd4fb89b245798d2db",
    "name": "NumberGenerator",
    "goal": 100,
    "step": 10,
    "status": "SUCCESS",
    "goalStepsMap": null,
    "result": [
        100, 90, 80, 70, 60, 50, 40, 30, 20, 10, 0
    ],
    "results": null,
    "bulkTask": false
}
```

**Delete Task**

Delete the task. 

[HTTP DELETE] http://localhost:9091/api/tasks/5fd9e8bd4fb89b245798d2db

Response:

```

```