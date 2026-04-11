# 🚀 QA GoRest Automation – Showcase with QABase

> **Highlights:** End-to-end REST API automation using [QABase](https://github.com/toobprojects/qabase-framework) `2.2.0`.  
> **Scope:** Demonstrates the QABase REST module with unified request/assertion chaining and config-driven default headers.

---

## 📖 About This Project
This repository is a **showcase project** for **REST API automation testing** using **QABase**.  
It demonstrates:
- ✅ Minimal Maven setup with **QABase Framework** as the parent.  
- ✅ Unified **RestClient** flow where calls and assertions chain from the same response object.  
- ✅ Config-based default headers via `qabase.rest.headers.*`.  
- ✅ **Allure reports** auto-generated from tests for traceability.  

> This project focuses on **REST API testing**.  
> A separate showcase for **Web UI testing** with QABase WebUI (Selenide) will follow soon.

---

## ⚙️ Prerequisites
- ☕ Java **17**  
- 📦 Apache Maven **3.8+**  

---

## 🔑 Authentication Setup
To run these tests, you will need a valid **GoRest API Token**.  
Generate one from the [GoRest API website](https://gorest.co.in/), then expose it in one of these ways:

```bash
export GOREST_AUTHORIZATION="Bearer your_generated_token_here"
```

For backward compatibility, the showcase also accepts:

```bash
export GOREST_TOKEN=your_generated_token_here
```

If `GOREST_AUTHORIZATION` is not already provided, the tests will convert `GOREST_TOKEN` into the configured `Authorization` header automatically.

---

## 📦 Minimal POM Setup
To get started with QABase, you only need:

```xml
<parent>
    <groupId>io.github.toobprojects</groupId>
    <artifactId>qabase-framework</artifactId>
    <version>2.2.0</version>
    <relativePath/> <!-- fetch from repository -->
</parent>

<dependencies>
    <!-- QABase REST -->
    <dependency>
        <groupId>io.github.toobprojects</groupId>
        <artifactId>qabase-rest</artifactId>
    </dependency>
</dependencies>

<build>
    <!-- Ensure Java tests are included -->
    <sourceDirectory>src/main/java</sourceDirectory>
    <testSourceDirectory>src/test/java</testSourceDirectory>
</build>
```

## 🛠️ QABase Rest Configuration

Project config (YAML) used by the showcase:

```yaml
qabase:
  rest:
    base-url: "https://gorest.co.in/public/v2"
    headers:
      content-type: "application/json"
      accept: "application/json"
      authorization: "${GOREST_AUTHORIZATION:}"
```

> Use config for common headers and reserve request-level header overrides for truly request-specific cases.


👉 That’s it! This minimal configuration ensures:
- QABase parent manages plugins & dependencies.  
- RestClient and response assertions are available out of the box.  
- Allure reporting and test lifecycle wiring are pre-configured.  

(Optional) Add **Lombok** if you prefer boilerplate-free Java (not required for QABase).

---

## 📝 Example Tests

### Create User
```java
user = RestClient.post("/users", TestDataFactory.randomUser())
        .created()
        .contentType()
        .attach()
        .as(User.class);

assertNotNull(user.getId(), "New user must have an id");
```

### Fetch User
```java
RestClient.get("/users/" + user.getId())
        .ok()
        .contentType()
        .fieldEq("id", Math.toIntExact(user.getId()))
        .attach();
```

👉 In QABase `2.2.0`, the object returned by `RestClient` is the primary REST test surface: request execution, assertions, attachments, and extraction all live in one fluent chain.

---

## 📊 Reporting with Allure
After running tests, QABase automatically generates **Allure Reports** under `target/allure-results`.

### Generate the report:

```bash
# 1) Property-based activation (auto-activates the profile)
mvn clean verify -Dallure.reports=true

# 2) Profile by ID (sets the same property under the hood)
mvn clean verify -Pallure-reports
```

### Serve the report locally:

```bash
mvn allure:serve
```

Sample report (screenshot):  
![Allure Report Sample](docs/allure-sample.jpg)

---

## 🔑 Key Takeaways
- Minimal setup with **QABase Framework** → parent POM does the heavy lifting.  
- Unified REST assertions directly from `RestClient`.  
- Config-based default headers reduce repeated request setup.  
- Auto-generated **Allure reporting** for beautiful test insights.  
- Java-friendly (works great with **Lombok** if you prefer).  

---

## 🌍 Next Steps
- [ ] Showcase project for **QABase WebUI** (Selenide-based Web UI automation).  
- [ ] Example repo with **QABase as a BOM** instead of a parent.  

---

👨‍💻 Built with ❤️ by **[TOOB Projects](https://github.com/toobprojects)** to simplify and accelerate **QA Automation** on the JVM.
