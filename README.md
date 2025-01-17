<img src="https://playlegend.net/wp-content/uploads/2023/10/logo-upated.png" style="display: block; margin: 0 auto;" alt="Logo">

### Minecraft Group Plugin -

Dieses Repository enthält die Lösung zur Bewerbungsaufgabe des PlayLegend Netzwerks.

---

## Aufgabenbeschreibung

Das Plugin dient als Gruppensystem, ähnlich wie Pex oder LuckPerms. Es soll Verwaltung von Spielergruppen und deren Rechte (Permissions) direkt im Spiel ermöglichen. 

### **Minimalanforderungen:**
1. Gruppenerstellung und -verwaltung im Spiel.
	1. Gruppen mit den folgenden Eigenschaften:
	   - Name
	   - Präfix
2. Spielerzuweisung zu Gruppen:
   - Permanent: Dauerhafte Zuweisung.
   - Temporär: z. B. 4 Tage, 7 Minuten, 23 Sekunden.
	1. Präfix-Anzeige der Gruppe:
	   - Im Chat.
	   - Beim Serverbeitritt.
1. Gruppenwechsel ohne Spielerkick.
2. Anpassbare Nachrichten über eine Config.
3. Command zum Anzeigen der aktuellen Gruppe des Spielers und die verbleibende Zeit (bei nicht Permanentem Rank).
4. Hinzufügen von Schildern, die individuelle Spielerinformationen anzeigen (z.B. Name und Rank).
5. Speicherung aller relevanten Daten in einer relationalen Datenbank.

### **Bonusaufgaben (Optional):**
1. **Gruppenberechtigungen:**
   - Definierbar und abfragbar über `#hasPermission`.
   - Unterstützung der „ * “-Berechtigung (has_op).
2. Mehrsprachige Unterstützung.
3. Tablist-Sortierung basierend auf Gruppen (Prio).
4. Scoreboard-Anzeige mit Gruppenzugehörigkeit.

---

## Anforderungen

1. **Definition of Done**:
   - Erfüllung aller Anforderungen.
   - Voll funktionsfähiges Plugin.
   - Unit Tests mit mindestens 15 % Code Coverage (inkl. wichtiger Funktionen).
   - Upload des Projekts auf GitHub.
2. **Allgemeine Anforderungen**:
   - Einhaltung von Java-Konventionen und Google Codestyle.
   - Clean Code: Verständlich, wartbar und erweiterbar.
   - Asynchrones I/O für hohe Performance.
   - Skalierbarkeit für viele Spieler.

---

## Projektstruktur

- **`src/main/java`**: Enthält den Quellcode des Plugins.
- **`src/test/java`**: Enthält Unit Tests.
- **`README.md`**: Diese Datei mit Projektinformationen.

---

# Database Tables
**Rank**:

rank_dpname = rank displayname<br/>
rank_id = automatisch erhöhter int<br/> 
prio = priorität für den tab<br/>
prefix = schrift vor dem namen

| rank_id     | rank_dpname | prio | prefix | color |
| ----------- | ----------- | ---- | ------ | ----- |
| *auto incr* | player      | 0    | PLAYER | blue  |

**Rank Permissions**:

rank_id = automatisch erhöhter int<br/>
has_op = "*" Rechte (ignoriert alles)

| rank_id          | permission        | has_op |
| ---------------- | ----------------- | ------ |
| (player rank id) | server.joinServer | false  |


**User**:

uuid = uuid vom spieler<br/>
display name = name vom spieler<br/>
rank id = derzeitiger rang<br/>
rank until = datum, bis wann der rank valide ist

| uuid | display_name | rank_id | rank_until |
| ---- | ------------ | ------- | ---------- |
|      |              | 0       | -1         |

**Signs**:

_id_ = automatisch erhöhter int<br/>
_world_ = welt, auf welcher das schild gesetzt wurde<br/>
_coordinates_ = x,y und z koordinate vom schild, um es 

| id          | world | coordinates |
| ----------- | ----- | ----------- |
| *auto incr* | world | [x,y,z]     |

# Installation