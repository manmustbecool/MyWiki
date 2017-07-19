# reference: http://www.gisremotesensing.com/2015/11/passing-r-variables-dynamically-to.html

library(shiny)
ui <- shinyServer(bootstrapPage(
  tags$script(src = "https://code.highcharts.com/highcharts.js"),
  tags$script(src = "https://code.highcharts.com/modules/exporting.js"),
  
  # handler to receive data from server
  tags$script(
    'var dataFromServer;
    Shiny.addCustomMessageHandler("SendObjectToClientDynamicCallbackHandler",
    function(variables) {
    dataFromServer = variables;
    });'
  ),
  # handler to receive custom data
  tags$div(id = "container", style = "min-width: 310px; height: 400px; top:200px; position: relative")
  , includeScript("charts.js")
  ))

server <- shinyServer(function(input, output, session) {  
  #global varible on x-axis  
  x <- 0       
  # Generate a new random variable object and send it back to the client  
  # handler function called 'SendObjectToClientDynamicCallbackHandler'  
  autoUpdate <- reactiveTimer(1000,session)
  
  observe({  
    autoUpdate()  
    x <<- x + 10  
    y = runif(1)  
    #pass data to client as object - x & y are passed to client from server on every second  
    variableToPassClient = sprintf('{"X":"%s",   
              "Y": "%s"               
              }', x, y)  
    session$sendCustomMessage(type="SendObjectToClientDynamicCallbackHandler",variableToPassClient)  
  })  
})  

options(shiny.host = "0.0.0.0")
options(shiny.port = 9998)
shinyApp(ui = ui, server = server)