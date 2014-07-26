Paprika
=======

Paprika is a JSON library for Scala that aims at simplifying JSON operation in Scala in a fashion similar to Javascript.  
Paprika provides JSON parsing and composition as well  as dynamic data member access.

## Usage

### Parsing Text

The following are all valid ways of parsing JSON using Paprika:

    Json("[ 15, true, null, { \"prop\": \"value\" }]")
    Json.fromFile("data.json")
    Json.fromUrl("http://example.com/api/data.json")

When invalid JSON is encountered, Paprika tries to let you know where the error occurred:

    A JSON parsing error occurred:
    char(27): 5e-3, 3.1,? -4.96 ]
                        ^


### Creating a Json Object

There are two ways of creating a `Json` object. The first is to parse JSON from a string or external source using the `Json` companion object. The second way is to pass any value to the `Json` constructor, which will attempt to wrap the value in a JSON structure. The following are equivalent:

    val parsed  = Json("[1, 2, 3]")
    val wrapped = new Json(List(1, 2, 3))


### Accessing Data Members

Much like JavaScript, Paprika allows dynamic access to JSON data members without knowing if they actually exist or what their type is. Paprika supports both dot and dictionary notation for property access. Array member access uses standard `()` notation. 

Say we have the following JSON schema in a file "data.json":

    {
        "title": "Example Schema",
        "type": "object",
        "properties": {
            "firstName": {
                "type": "string"
            },
            "lastName": {
                "type": "string"
            },
            "age": {
                "description": "Age in years",
                "type": "integer",
                "minimum": 0
            }
        },
        "required": ["firstName", "lastName"]
    }

Here's how you can use Paprika to parse it and access its data members:

    val json = Json.fromFile("data.json")
    
    println(json.title)      // prints out "Example Schema" - dot notation
    println(json("title"))   // also prints out "Example Schema" - dictionary notation

    println(json.properties.age.minimum)       // prints out "0" - chaining dot notation
    println(json.properties("age").minimum)    // also prints out "0" - alternating between dot and dictionary notation

    println(json.required(1))      // prints out "lastName" - array member access
    println(json("required")(1))   // also prints out "lastName" - combining dictionary notation with array member access

When a data member that does not exist is accessed, the object `JsonUndefined` is returned.

    val json = Json("{ }")

    println(json.prop) // prints out "undefined"       


### Data Types

By default, all data members returned from a `Json` object are also `Json` objects. However, `Json` provides both implicit and explicit conversions:

    val json = Json("{ \"data\": 2.74 }")

    val a = json.data                  // default, a is a Json object
    val b = json.data.toDouble         // explicit conversion, b is Double(2.74)
    val c : Double = json.data         // implicit conversion, c is Double(2.74)


### Iterator

If the JSON structure is an array, then calling `.iterator()` will return an iterator for the array.  
This allows `Json` objects to be used in for loops:

    val json = Json("[ 1, 9 ,0 6, 2 ]")
    
    for (n <- json) {
      println(n)
    }

This holds true for data members as well:

    val json = Json("{ \id"\": 12, \"data\": [ 1, 9 ,0 6, 2 ] }")
    
    for (n <- json.data) {
      println(n)
    }


### Composing JSON String

You can compose a JSON string from an existing `Json` object using the `.makeString()` method:

    val json = new Json(List(1, 2, 3))
    println(json.makeString) // prints out: "[1,2,3]" 

You can also use the static method `Json.makeJSON()` to convert any object to a JSON string:

    val nums = List(1,2,3)
    val json = new Json(nums)

    println(Json.makeJSON(json)) // prints out: "[1,2,3]"
    println(Json.makeJSON(nums)) // prints out: "[1,2,3]"

When converting regular objects to JSON strings, `List`s are converted to JSON arrays and `Map`s are converted to JSON objects. All primitive data types are kept the same; when an incompatible object is found, it is stored as a string by calling that object's `.toString()` method.

All generated JSON strings are minified.
