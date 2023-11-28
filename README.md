# asp
asp(Auto Shutdown Plugin) repository.

## 使用例
必要なもの
- linux
- papermc
- velocity
- minecraft
- asp
- nc(netcatコマンド)
- docker

1. 最初にDockerコンテナをパパっと作ります
(DockerFileの例)
```Dockerfile
FROM openjdk:17-jdk-slim

LABEL version="1.20.2-299"
LABEL description="minecraft paper server"

ENV PORT=25565
ENV MEMORY=4G
ENV MAXGCPAUSEMILLIS=200
ENV G1NEWSIZEPARCENT=30
ENV G1MAXNEWSIZEPARCENT=40
ENV G1HEAPREGIONSIZE=8M
ENV G1RESERVEPERCENT=20
ENV G1HEAPWASTEPERCENT=5
ENV G1MIXEDGCCOUNTTARGET=4
ENV INITIATINGHEAPOCCUPANCYPERCENT=15
ENV G1MIXEDGCLIVETHRESHOLDPERCENT=90
ENV G1RSETUPDATINGPAUSETIMEPERCENT=5
ENV SURVIVORRATIO=32
ENV MAXTENURINGTHRESHOLD=1
ENV VERSION=1.20.2
ENV BUILD=299

EXPOSE 25565/tcp

WORKDIR /opt/
RUN apt-get update &&\
        apt-get -y install wget netcat &&\
        rm -rf /var/lib/apt/lists/* &&\
        wget https://api.papermc.io/v2/projects/paper/versions/$VERSION/builds/$BUILD/downloads/paper-$VERSION-$BUILD.jar &&\
        apt-get purge -y wget

COPY entry.sh /opt/
RUN chmod +x /opt/entry.sh
RUN mkdir /data

ENTRYPOINT ["/bin/sh", "-c", "/opt/entry.sh"]
```
2. 次にentryファイルを作ります。ここで、**Docker起動時にAlways入ってる(Restart: always)ことが前提となります**
```sh
#!/bin/sh

cd /data
nc -l -p $PORT
exec java -Xms$MEMORY -Xmx$MEMORY -XX:+UseG1GC -XX:+ParallelRefProcEnabled -XX:MaxGCPauseMillis=$MAXGCPAUSEMILLIS -XX:+UnlockExperimentalVMOptions -XX:+DisableExplicitGC -XX:+AlwaysPreTouch -XX:G1NewSizePercent=$G1NEWSIZEPARCENT -XX:G1MaxNewSizePercent=$G1MAXNEWSIZEPARCENT -XX:G1HeapRegionSize=$G1HEAPREGIONSIZE -XX:G1ReservePercent=$G1RESERVEPERCENT -XX:G1HeapWastePercent=$G1HEAPWASTEPERCENT -XX:G1MixedGCCountTarget=$G1MIXEDGCCOUNTTARGET -XX:InitiatingHeapOccupancyPercent=$INITIATINGHEAPOCCUPANCYPERCENT -XX:G1MixedGCLiveThresholdPercent=$G1MIXEDGCLIVETHRESHOLDPERCENT -XX:G1RSetUpdatingPauseTimePercent=$G1RSETUPDATINGPAUSETIMEPERCENT -XX:SurvivorRatio=$SURVIVORRATIO -XX:+PerfDisableSharedMem -XX:MaxTenuringThreshold=$MAXTENURINGTHRESHOLD -Dusing.aikars.flags=https://mcflags.emc.gs -Daikars.new.flags=true -jar /opt/paper-$VERSION-$BUILD.jar nogui
```
3. /dataにディレクトリマウントして、そこに作られたpluginsディレクトリにasp.jarを導入して完成

ncでポートを監視しておき、受信したらサーバーを起動する　という仕組みです。　ただこのままだとプレイヤーは一度切断され、サーバー起動を待ってから参加することになります。
何かしら配慮をしてあげてください。
