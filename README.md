# Documentation de l'application medilabo

#### 1. Lancement
Pour lancer l'application il vous faudra docker installe sur votre machine.

- Au premier lancement vous pouvez lancer la commande 
`docker-compose up -d`

- Si vous apportez des modifications sur le code, pour redeployer vos changements lancer la commande

` docker-compose up --force-recreate --build -d`

#### 2. Les composantes

L'application est constituee des microservices (ms) suivants:

- la gateway 8080
- le ms front (UI) 8081
- le ms patient 8082
- le ms note 8083
- le ms evaluation risque 8084
- le ms authentication 8085
- le cluster mongo sur le port 27017
- le cluster MySQL sur le port 3308. Les identifiants de connexion a la BD patient sont: host=localhost, user=user1 et password=passer123

Pour se connecter depuis le ms-front vous pouvez utiliser l'un des identifiants suivants:
- username=omar et password=passer@123
- username=robin et password=passer

#### 3. Outils

Vous pouvez utiliser l'outil [Mongo Compass](https://www.mongodb.com/products/tools/compass) pour visualiser les donnees de la connexion, renseigner host=localhost et port=27017

Pour la BD MySQL vous pouvez utiliser l'outil [DB Eaver](https://dbeaver.io/) ou equivalent

Pour manager les containers vous pouvez y aller soit via le terminal ou plus facilement avec un outil graphique tel que [Docker Desktop](https://www.docker.com/products/docker-desktop/)
