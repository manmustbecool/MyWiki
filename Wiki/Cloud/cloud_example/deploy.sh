#!/bin/bash

echo "Welcome"

# sudo snap disable microk8s
# sudo snap enable microk8s

sudo microk8s.enable dns dashboard
sudo microk8s.enable storage

sudo iptables -P FORWARD ACCEPT

microk8s.enable registry
# http://localhost:32000/

microk8s.docker build -t localhost:32000/py_app_img:latest .
microk8s.docker push localhost:32000/py_app_img:latest

microk8s.docker image ls

kubectl delete -f my_kube.yaml
kubectl create -f my_kube.yaml

kubectl get pods -o wide
# my-py-app-svc-t6s2g   1/1       Running   0          14m       10.1.1.206   u18-virtualbox   <none>

kubectl get services
# my-py-app-svc    NodePort    10.152.183.51   <none>        81:31976/TCP   37s

kubectl get replicationcontrollers

sleep 5
POD=""
while [ "$POD" = ""  ]
do
    # forward ambassador service to from port 80 to 8080. we can access it from port 8080
    POD=`kubectl get pods --selector=app=my-py-app --field-selector=status.phase=Running| awk '{print $1}' | tail -1`
    if [ "$POD" = "" ]
    then
            echo "Waiting anomaly service deployment finish..."

    else
            break
    fi
    sleep 3
done
echo $POD

xdg-open 'http://localhost:8080/api/v1/namespaces/kube-system/services/https:kubernetes-dashboard:/proxy/'
sleep 1

xdg-open 'http://0.0.0.0:9081/'
sleep 1

xdg-open 'http://0.0.0.0:9081/addpy_api?x=3&y=4'
sleep 2

POD=`kubectl get pods --selector=app=my-py-app --field-selector=status.phase=Running| awk '{print $1}' | tail -1`
kubectl port-forward $POD 9081:81







