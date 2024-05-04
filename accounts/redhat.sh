sudo apt-get install --reinstall ca-certificates
sudo mkdir /usr/local/share/ca-certificates/cacert.org
sudo wget -P /usr/local/share/ca-certificates/cacert.org http://www.cacert.org/certs/root.crt http://www.cacert.org/certs/class3.crt
sudo update-ca-certificates
git config --global http.sslCAinfo /etc/ssl/certs/ca-certificates.crt
