### 1. Get Linux
FROM alpine:3.7

ARG mydns=193.181.14.10

### the dns is not added in the .conf for the seperated RUN
RUN echo 'nameserver '$mydns >> /etc/resolv.conf
RUN echo "start  build process ... "
RUN cat /etc/resolv.conf

### RUN echo "http://dl-cdn.alpinelinux.org/alpine/edge/testing" >> /etc/apk/repositories

### 2. Get Java via the package manager
RUN echo 'nameserver '$mydns >> /etc/resolv.conf \
&& apk update \
&& apk upgrade \
&& apk add --no-cache bash \
&& apk add --no-cache --virtual=build-dependencies unzip \
&& apk add --no-cache curl \
&& apk add --no-cache openjdk8
###  && apk add --no-cache openjdk8-jre ### for jre only

#### OPTIONAL : 4. SET JAVA_HOME environment variable, uncomment the line below if you need it
ENV JAVA_HOME="/usr/lib/jvm/java-1.8-openjdk"

### 3. Get Python, PIP
RUN echo 'nameserver '$mydns >> /etc/resolv.conf \
&& apk add --no-cache python3 \
&& python3 -m ensurepip \
&& pip3 install --upgrade pip setuptools \
&& rm -r /usr/lib/python*/ensurepip && \
if [ ! -e /usr/bin/pip ]; then ln -s pip3 /usr/bin/pip ; fi && \
if [[ ! -e /usr/bin/python ]]; then ln -sf /usr/bin/python3 /usr/bin/python; fi && \
rm -r /root/.cache

### Get Flask for the app
RUN echo 'nameserver '$mydns >> /etc/resolv.conf \
&& pip install --trusted-host pypi.python.org flask

### Get py4y for allowing call Java from python
RUN echo 'nameserver '$mydns >> /etc/resolv.conf \
&& pip install --trusted-host pypi.python.org py4j


EXPOSE 81
ADD py_app.py /
ADD AddPy.java /

### for a single line CMD
### CMD ["python", "py_app.py"]

### For multiple line CMDs, put them in a shell file
ADD container_start.sh /
CMD chmod +x container_start.sh && ./container_start.sh

