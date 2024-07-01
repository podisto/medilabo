# Documentation de l'application medilabo

#### 1. Lancement
Pour lancer l'application il vous faudra docker installe sur votre machine.

- Au premier lancement vous pouvez lancer la commande 
`docker-compose up -d`

- Si vous apportez des modifications sur le code, pour redeployer vos changements lancer la commande

`docker-compose up --force-recreate --build -d`

#### 2. Les composantes

L'application est constituee des microservices (ms) suivants:

- la gateway 8080
- le ms front (UI) 8081
- le ms patient 8082
- le ms note 8083
- le ms authentication 8085