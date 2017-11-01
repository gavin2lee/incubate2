mkdir -p /home/gavin/Dev/tools/data/redis/rd5410
mkdir -p /home/gavin/Dev/tools/data/redis/rd5420
mkdir -p /home/gavin/Dev/tools/data/redis/sentinel-1
mkdir -p /home/gavin/Dev/tools/data/redis/sentinel-2

sudo /opt/redis/redis-3.2.6/bin/redis-server /home/gavin/Dev/tools/data/redis/rd5410/redis-5410.conf
sudo /opt/redis/redis-3.2.6/bin/redis-server /home/gavin/Dev/tools/data/redis/rd5420/redis-5420.conf
sudo /opt/redis/redis-3.2.6/bin/redis-sentinel /home/gavin/Dev/tools/data/redis/sentinel-1/sentinel-1.conf
sudo /opt/redis/redis-3.2.6/bin/redis-sentinel /home/gavin/Dev/tools/data/redis/sentinel-2/sentinel-2.conf


sudo /opt/redis/bin/redis-server /home/gavin/Dev/tools/data/redis/rd5410/redis-5410.conf
sudo /opt/redis/bin/redis-server /home/gavin/Dev/tools/data/redis/rd5420/redis-5420.conf
sudo /opt/redis/bin/redis-sentinel /home/gavin/Dev/tools/data/redis/sentinel-1/sentinel-1.conf
sudo /opt/redis/bin/redis-sentinel /home/gavin/Dev/tools/data/redis/sentinel-2/sentinel-2.conf