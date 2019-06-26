function loadHospitals() {

    this.source = null;

    this.start = function () {

        var commentTable = document.getElementById("hotels");

        this.source = new EventSource("/hospital/stream");

        this.source.addEventListener("message", function (event) {

            // These events are JSON, so parsing and DOM fiddling are needed
            var hospital = JSON.parse(event.data);

            var row = commentTable.getElementsByTagName("tbody")[0].insertRow(0);
            var cell0 = row.insertCell(0);
            var cell1 = row.insertCell(1);
            var cell2 = row.insertCell(2);

            cell0.className = "author-style";
            cell0.innerHTML = hospital.author;

            cell1.className = "text";
            cell1.innerHTML = hospital.message;

            cell2.className = "date";
            cell2.innerHTML = hospital.timestamp;

        });

        this.source.onerror = function () {
            this.close();
        };

    };

    this.stop = function () {
        this.source.close();
    }

}

hospital = new loadHospitals();

/*
 * Register callbacks for starting and stopping the SSE controller.
 */
window.onload = function () {
    hospital.start();
};
window.onbeforeunload = function () {
    hospital.stop();
};