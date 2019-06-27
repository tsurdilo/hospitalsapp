function loadHospitals() {

    this.source = null;

    this.start = function () {

        var hospitalsTable = document.getElementById("hospitals");

        this.source = new EventSource("/hospital/tail");

        this.source.addEventListener("message", function (event) {

            // These events are JSON, so parsing and DOM fiddling are needed
            var hospital = JSON.parse(event.data);

            var row = hospitalsTable.getElementsByTagName("tbody")[0].insertRow(0);
            var cell0 = row.insertCell(0);
            var cell1 = row.insertCell(1);
            var cell2 = row.insertCell(2);
            var cell3 = row.insertCell(3);

            cell0.className = "text";
            cell0.innerHTML = hospital.id;

            cell1.className = "text";
            cell1.innerHTML = hospital.name;

            cell2.className = "text";
            cell2.innerHTML = hospital.address;

            cell3.className = "text";
            cell3.innerHTML = hospital.zip;

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