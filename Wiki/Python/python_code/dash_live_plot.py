import dash
from dash.dependencies import Output, Input
import dash_core_components as dcc
import dash_html_components as html
import plotly
import random
import plotly.graph_objs as go
from collections import deque

X = deque(maxlen=10)
X.append(1)
Y1 = deque(maxlen=10)
Y2 = deque(maxlen=10)
Y1.append(1)
Y2.append(1)

app = dash.Dash(__name__)
app.layout = html.Div(
    [
        dcc.Graph(id='live-graph', animate=True),
        dcc.Interval(id='graph-update', interval=2*1000),
    ]
)

@app.callback(Output('live-graph', 'figure'),
              [Input('graph-update', 'n_intervals')])
def update_graph_scatter(n):
    X.append(X[-1]+1)
    Y1.append(Y1[-1]+Y1[-1]*random.uniform(-0.1, 0.1))
    Y2.append(Y2[-1]+Y2[-1]*random.uniform(-0.1, 0.1))

    trace1 = go.Scatter(
        x=list(X),
        y=list(Y1),
        name='Scatter',
        mode='lines+markers'
    )

    trace2 = go.Scatter(
        x=list(X),
        y=list(Y2),
        name='Scatter',
        mode='lines'
    )

    data = [trace1, trace2]

    return {'data': data,
            'layout': go.Layout(xaxis=dict(range=[min(X), max(X)]),
                                yaxis=dict(range=[min(min(Y1), min(Y2)), max(max(Y1), max(Y2))]))
            }


if __name__ == '__main__':
    app.run_server(debug=True)