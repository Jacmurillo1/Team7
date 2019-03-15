
      // Load the Visualization API and the corechart package.
      google.charts.load('current', {'packages':['corechart']});

      // Set a callback to run when the Google Visualization API is loaded.
      google.charts.setOnLoadCallback(drawChart);

      // Callback that creates and populates a data table,
      // instantiates the pie chart, passes in the data and
      // draws it.

      function fetchMessageData() {
        fetch("/messagechart")
        .then((response) => {
          return response.json();
        })
        .then((msgJson) => {
          console.log(msgJson);
        });
      }
      
      fetchMessageData();



      function drawChart() {

        // Create the data table.
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Name');
        data.addColumn('number', 'Messages');
        data.addRows([
          ['John', 6],
          ['Tyler', 10],
          ['Justin', 7],
          ['Megan', 4],
          ['Melanie', 8]
        ]);

        // Set chart options
        var chart_options = {
                       'title':'Number of messages per user',
                       'is3D':true,
                       'width':800,
                       'height':400};

        // Instantiate and draw our chart, passing in some options.
        var chart = new google.visualization.BarChart(document.getElementById('chart'));
        chart.draw(data, chart_options);
      }
