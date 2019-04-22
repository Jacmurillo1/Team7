
      // Load the Visualization API and the corechart package.
      google.charts.load('current', {'packages':['corechart']});

      // Set a callback to run when the Google Visualization API is loaded.
      google.charts.setOnLoadCallback(fetchMessageData);

      // Callback that creates and populates a data table,
      // instantiates the pie chart, passes in the data and
      // draws it.

      function fetchMessageData() {
        fetch("/messagechart")
          .then((response) => {
            return response.json();
          })
          .then((msgJson) => {
            var msgData = new google.visualization.DataTable();
          //defining columns for the DataTable instances
            msgData.addColumn('date','Data');
            msgData.addColumn('number','Message Count');

            for (i = 0; i < msgJson.length; i++) {
              msgRow = [];
              var timestampAsDate = new Date (msgJson[i].timestamp);
              var totalMessages = i + 1;
            //TODO add the formatted values to msgRow array by using JS' push method
              msgRow.push(timestampAsDate);
              msgRow.push(totalMessages);
            //console.log(msgRow);
              msgData.addRow(msgRow);

            }
            //console.log(msgData);
            drawChart(msgData);
          });
      }


      function drawChart(table) {

        // Set chart options
        var chart_options = {
                       'title':'Number of messages per user',
                       'is3D':true,
                       'width':800,
                       'height':400};

        // Instantiate and draw our chart, passing in some options.
        var chart = new google.visualization.BarChart(document.getElementById('chart'));
        chart.draw(table, chart_options);
      }
