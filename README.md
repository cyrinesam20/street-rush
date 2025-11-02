# Street Rush 🏃‍♂️

Jeu inspiré de Subway Surfers développé dans le cadre du module Design Patterns.

## 📝 Description

Street Rush est un jeu de type "endless runner" où le joueur doit éviter des obstacles tout en collectant des pièces et des power-ups.

## 👥 Membres du Groupe

- [TON NOM]
- [Ajouter les autres membres]

## 🎮 Technologies Utilisées

- **Langage** : Java 17
- **Framework GUI** : Swing (Java standard)
- **Build** : Maven
- **Logging** : System custom logger

## 🎯 Design Patterns Implémentés

### 1. **State Pattern** ✅
- **États du jeu** : MenuState, PlayingState, PausedState, GameOverState
- **États du joueur** : RunningState, JumpingState, SlidingState, CrashedState
- Transitions tracées dans les logs

### 2. **Decorator Pattern** ✅
- SpeedBoostDecorator : Augmente la vitesse
- MagnetDecorator : Attire les pièces
- ShieldDecorator : Protège des collisions
- DoubleCoinDecorator : Double les pièces collectées

### 3. **Composite Pattern** ✅
- GameObjectComposite : Gère la hiérarchie des objets
- Contient obstacles, power-ups, et pièces

### 4. **Factory Pattern** ✅
- RandomObstacleFactory : Crée Barrier, Train, Cone
- RandomPowerUpFactory : Crée les power-ups aléatoires

### 5. **Singleton Pattern** ✅
- GameLogger : Système de logging unique

## 🚀 Installation

### Prérequis
- JDK 17 ou supérieur
- Maven 3.6+

### Étapes

1. **Cloner le projet**
```bash
git clone [URL_DU_REPO]
cd street-rush-game
```

2. **Compiler**
```bash
mvn clean compile
```

3. **Exécuter**
```bash
mvn exec:java -Dexec.mainClass="com.streetrush.main.Game"
```

Ou via IntelliJ : Clic droit sur `Game.java` → Run

## 🎮 Contrôles

- **← →** : Déplacer à gauche/droite (changer de voie)
- **↑** : Sauter
- **↓** : Glisser
- **ESPACE** : Démarrer (Menu) / Redémarrer (Game Over)
- **ESC** : Pause / Reprendre
- **Q** : Quitter (Pause/Game Over)

## 📊 Structure du Projet

```
src/main/java/com/streetrush/
├── main/
│   └── Game.java                    # Classe principale
├── states/
│   ├── game/                        # États du jeu
│   │   ├── GameState.java
│   │   ├── MenuState.java
│   │   ├── PlayingState.java
│   │   ├── PausedState.java
│   │   └── GameOverState.java
│   └── player/                      # États du joueur
│       ├── PlayerState.java
│       ├── RunningState.java
│       ├── JumpingState.java
│       ├── SlidingState.java
│       └── CrashedState.java
├── entities/
│   ├── Player.java
│   ├── Coin.java
│   └── GameObject.java
├── decorators/
│   ├── PlayerDecorator.java
│   ├── SpeedBoostDecorator.java
│   ├── MagnetDecorator.java
│   ├── ShieldDecorator.java
│   └── DoubleCoinDecorator.java
├── factories/
│   ├── ObstacleFactory.java
│   ├── RandomObstacleFactory.java
│   ├── PowerUpFactory.java
│   └── RandomPowerUpFactory.java
├── obstacles/
│   ├── Obstacle.java
│   ├── Barrier.java
│   ├── Train.java
│   └── Cone.java
├── powerups/
│   ├── PowerUp.java
│   ├── SpeedBoost.java
│   ├── Magnet.java
│   ├── Shield.java
│   └── DoubleCoin.java
├── composite/
│   └── GameObjectComposite.java
├── utils/
│   ├── GameLogger.java
│   └── Vector3.java
└── ui/
    └── GamePanel.java
```

## 📋 Logs

Le jeu génère automatiquement un fichier `game.log` qui trace :
- Changements d'états du jeu
- Changements d'états du joueur
- Application/expiration des décorateurs
- Événements importants (collisions, collecte)

Exemple :
```
[2024-12-15 14:23:45] [INFO] Game started
[2024-12-15 14:23:47] [STATE] Game: MENU -> PLAYING
[2024-12-15 14:23:50] [STATE] Player: IDLE -> RUNNING
[2024-12-15 14:23:52] [DECORATOR] SpeedBoostDecorator applied to Player
```

## 🎨 Captures d'écran

[Ajouter des captures d'écran du jeu]

## 🐛 Problèmes Connus

- Aucun pour le moment

## 📈 Améliorations Futures

- Ajouter des niveaux de difficulté
- Système de high scores
- Plus de types d'obstacles
- Animations plus fluides
- Musique et effets sonores

## 📄 Licence

Projet académique - Université [NOM]

---

**Bon jeu ! 🎮**