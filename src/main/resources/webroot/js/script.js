var eb = new vertx.EventBus("http://localhost:8484/eventbus");
eb.onopen = function () {
    console.log("Done!");
}

function testclick() {
	console.log('CLICKED');
	eb.send('test', {"test": "test"}, function(data) {
		console.log('success');
		console.log(data);
		
	});
}