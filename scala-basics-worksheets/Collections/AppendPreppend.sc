// prepend: +: 
//  append: :+  
//     add: ++

/** vectors
 *  vectors are scalas default indexed collection 
 *  behind every vector is a tree of arrays
 *  this gives us a constant time of appeneding and prepending
 */
val vector = Vector(1, 2, 3, 4)
    0 +: vector
    vector :+ 0
    vector ++ Vector(1, 2, 3, 4)

/** sets
 *  sets are not indexed in any order
 *  can't prepend or append to something with no order
 *  you can, however, add to a set 
 */
val set = Set(1, 2, 3, 4)
    set + 0
    set ++ Set(1, 2, 3, 4)

/** maps
 *  can't prepend or append to maps 
 *  you can, however, add to a map
 */
var map = Map(1 -> "A", 2 -> "B", 3 -> "C")
    map += (4 -> "D")
    map ++= Map( 4 -> "replaces 4 -> D", 5 -> "E", 6 -> "F", 7 -> "G", 8 -> "H", 9 -> "I", 10 -> "J")
    map

/** lists
 *  have speacial fancy syntx :::
 *  you can both append and prepend to lists as excpeted
 *  you can also use a combination of list specific syntax 
 *  or syntx for append and prepend as shown above
 */   
val list = List(1, 2, 3)
    0::list
    0 +: list :+ 4
    list.+:(-4)
    1 :: 2 :: 3 :: Nil

    val list1 = List(1, 2, 3)
    val list2 = List(4, 5, 6)
    val list3 = List(7, 8, 9)

    list1++(list2)++(list3)
    list1:::(list2):::(list3)
    list1.:::(list2).:::(list3)








