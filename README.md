# DCode - A Java Code Editor with Object Visualization and More

**DCode** is a feature-rich code editor designed specifically for Java programming. It combines a clean UI with powerful tools such as a syntax-aware editor, real-time object visualization, integrated terminal, and in-app documentation.

---

## 🧠 Features Overview

### 🔹 File Explorer
- Shows a tree view of your working directory
- Full file operations: create, delete, rename, move
- Operate via keyboard shortcuts, context menus, or top control buttons

### 🔹 Java-Aware Code Editor
- Auto indentation and auto bracket closing
- Syntax highlighting and error highlighting for Java
- Autocomplete for keywords and basic context-based suggestions (WIP)
- Abbreviation support (e.g., `sout` → `System.out.println()`)

### 🔹 Objects Pane (Static Variable Visualizer)
- Visualize static variables as colored nodes representing data types
- Support for:
  - Arrays → Heaps
  - 2D Arrays → Graphs
- Displays additional analysis such as:
  - Is the graph bipartite?
  - Are there cycles?

> 🛠️ Work in progress: Visualization of non-static variables

### 🔹 Terminal (Built-in Shell)
- Simulates a typical terminal
- Supports standard shell commands
- Used for compiling and running Java code
- Top-bar buttons: **Save**, **Compile**, **Run**, **Compile & Run**

### 🔹 Message Box
- Read-only display showing info about the selected node in Objects pane
- Includes insights like data structure properties and graph characteristics

### 🔹 Help Window
- Pop-up documentation window with content on:
  - Programming languages (Java, C++, Python)
  - Data structures (with function implementations in multiple languages)

### 🔹 Menu Bar & Customization
- Menus: **File**, **Edit**, **View**, **Code**, **Run**, **Tools**, **Window**, **Help**
- Settings allow:
  - Theme selection (Dark, Monokai, Solaris White, etc.)
  - Abbreviation customization: Add/Edit/Delete mappings

### 🔹 Multi-File Support
- Open multiple files simultaneously
- Easily switch between open files using tab-like buttons above the editor

---

## 💻 Tech Stack

- **Java**
- **JavaFX** (for UI rendering and interactivity)

---

## 🛣️ Roadmap

- [ ] Context-aware autocomplete improvements
- [ ] Support for visualizing non-static variables
- [ ] Support for more languages and themes
- [ ] Plugin system for custom extensions

---

## 📷 Screenshots
*(To be added soon)*

---

## 🧩 Contribution

Contributions are welcome! If you have ideas or improvements, feel free to fork the repo and submit a pull request.

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🙌 Acknowledgments

Thanks to the open-source community and everyone contributing ideas and feedback!

---

## 🔗 Links

- [GitHub Repo](https://github.com/Alvin0305/DCode)
