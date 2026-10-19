## Spring Boot Application Architecture
```mermaid
graph LR
  Client(Browser/Postman) -->|HTTP| Controller
  Controller -->|Usa| Repository
  Repository -->|Esegue query SQL| Database(PostgreSQL)
```
```mermaid
graph LR
    %% Definizione degli Stili
    classDef client fill:#f9f6f0,stroke:#333,stroke-width:2px;
    classDef web fill:#d4e157,stroke:#333,stroke-width:2px;
    classDef logic fill:#4fc3f7,stroke:#333,stroke-width:2px;
    classDef data fill:#ffb74d,stroke:#333,stroke-width:2px;
    classDef db fill:#e0e0e0,stroke:#333,stroke-width:2px;

    %% Nodi e Sotto-grafi
    subgraph 1. Client
        A[💻 Angular / Postman]:::client
    end

    subgraph 2. Esposizione Web
        B(👮 @RestController):::web
    end

    subgraph 3. Business Logic
        C{🧠 @Service}:::logic
        M((🗣️ @Component Mapper)):::logic
    end

    subgraph 4. Accesso Dati
        R[🗄️ @Repository]:::data
    end

    subgraph 5. Database
        DB[(🪞 PostgreSQL DB)]:::db
    end

    %% Relazioni (Il flusso)
    A <-->|1. Richiesta/Risposta HTTP <br> APIResponse| B
    B <-->|2. Passa / Riceve <br> Record DTO| C
    C -->|3. Chiede traduzione| M
    M -.->|4. DTO ⇄ Entity| C
    C <-->|5. Passa / Riceve <br> Entity| R
    R <-->|6. Genera Query SQL| DB
```
## Spring Boot Application Architecture with Service Layer
```mermaid
graph LR
    Client --> |HTTP JSON -DTO-| Controller
    Controller --> |Chiama| Service
    Service --> |Trasforma -Mapper-| Service
    Service --> |Chiama| Repository
    Repository --> |SQL -Entity-| Database
```

## Pattern JSend
```mermaid
graph TD
  subgraph Frontend Angular
    A[HttpClient]
  end

  subgraph API_Contract [Il Contratto JSend]
    B((APIResponse))
  end

  subgraph Backend Spring Boot
    C[ResourcesController]
    D[TriageColorService]
    E[TriageColorMapper]
    F[TriageColorRepository]
  end

  A -->|Richiede Dati| C
  C -->|Restituisce| B
  B -->|Legge| A

  C -->|1. Chiama| D
  D -->|2. Usa| F
  D -->|3. Usa| E
  E -->|Crea| DTO(TriageColorDTO)
  D -->|Restituisce Lista DTO| C
```

## Componenti Springboot

Ecco il contenuto pronto per essere inserito nelle tue prossime due slide. Ho mantenuto un formato visivo molto chiaro e conciso, perfetto per essere proiettato in aula.

---

### Slide 1: Riassunto dell'Ecosistema Spring Boot 🧩

**Titolo:** I Mattoni Fondamentali (Chi fa cosa)

| Annotazione / Tipo | Ruolo Metaforico | Compito Principale |
| --- | --- | --- |
| **`@SpringBootApplication`** | 🚀 **Il Motore** | Punto d'ingresso. Avvia il server, carica le configurazioni e scansiona il codice. |
| **`@RestController`** | 👮 **Il Vigile Urbano** | Intercetta le richieste HTTP (GET, POST), smista il traffico al Service e restituisce il JSON. |
| **`@Service`** | 🧠 **Il Cervello** | Contiene la logica di business. Prende decisioni, coordina il Mapper e il Repository. |
| **`Record` (DTO)** | 💼 **La Valigetta** | Contenitore immutabile e sicuro. Fa viaggiare i dati "puliti" da e verso l'esterno. |
| **`@Component` (Mapper)** | 🗣️ **Il Traduttore** | Converte i dati dalla lingua del DB (Entity) a quella dell'utente (DTO) e viceversa. |
| **`@Repository`** | 🗄️ **L'Archivista** | Dialoga con il database. Genera ed esegue le query SQL in automatico (CRUD). |
| **`@Entity`** | 🪞 **Lo Specchio** | La rappresentazione Java (classe) di una specifica tabella del database relazionale. |

---

### Slide 2: Il Flusso dei Dati (Diagramma Architetturale) 🔄

**Titolo:** Il Viaggio di una Richiesta HTTP

*(Puoi inserire questo codice Mermaid direttamente nel tuo file Markdown o in un visualizzatore online come mermaid.live per incollare l'immagine nella slide).*

```mermaid
graph LR
    %% Definizione degli Stili
    classDef client fill:#f9f6f0,stroke:#333,stroke-width:2px;
    classDef web fill:#d4e157,stroke:#333,stroke-width:2px;
    classDef logic fill:#4fc3f7,stroke:#333,stroke-width:2px;
    classDef data fill:#ffb74d,stroke:#333,stroke-width:2px;
    classDef db fill:#e0e0e0,stroke:#333,stroke-width:2px;

    %% Nodi e Sotto-grafi
    subgraph 1. Client
        A[💻 Angular / Postman]:::client
    end

    subgraph 2. Esposizione Web
        B(👮 @RestController):::web
    end

    subgraph 3. Business Logic
        C{🧠 @Service}:::logic
        M((🗣️ @Component Mapper)):::logic
    end

    subgraph 4. Accesso Dati
        R[🗄️ @Repository]:::data
    end

    subgraph 5. Database
        DB[(🪞 PostgreSQL DB)]:::db
    end

    %% Relazioni (Il flusso)
    A <-->|1. Richiesta/Risposta HTTP <br> APIResponse| B
    B <-->|2. Passa / Riceve <br> Record DTO| C
    C -->|3. Chiede traduzione| M
    M -.->|4. DTO ⇄ Entity| C
    C <-->|5. Passa / Riceve <br> Entity| R
    R <-->|6. Genera Query SQL| DB

```

**Spiegazione da dare a voce durante questa slide:**

1. Il Client chiede un dato (es. Colori Triage).
2. Il **Controller** riceve la chiamata e, non sapendo come accedere al DB, passa la palla al **Service**.
3. Il **Service** chiede al **Repository** le **Entity**.
4. Ottenute le **Entity** dal DB, il **Service** le passa al **Mapper** chiedendo di tradurle in **DTO** (più sicuri e puliti).
5. Il **Service** ridà i **DTO** al **Controller**.
6. Il **Controller** li impacchetta in un' `APIResponse` e li spedisce ad Angular sotto forma di JSON.
7. 