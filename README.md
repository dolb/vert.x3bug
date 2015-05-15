# vertx3-OutOfMemoryError

While sending simple echo message to SockJS handler I'm getting an error while I try to fetch message headers.

It happens only for a clustered vert.x (while I initialized sockJS and a message handler on a single verticle everything was fine).


## Test how to
You can eighter build the jar yourself or use the one from target directory.

1) java -jar ./target/bug_buffer_impl_vertx-0-fat.jar

2) Open your browser and go to localhost:8484/test.html

3) Click the CLICKME button to send a message to event bus and produce an error message on the servers end.

That would be all ;)
