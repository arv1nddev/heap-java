# Heap Sort in Java

This repository contains a clean and modular implementation of the **Heap data structure** and **Heap Sort algorithm** in Java.

Built for easy understanding, extensibility, and future CLI/GUI enhancement.


## Project Structure

heap-java/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               ├── ds/
│               │   └── Heap.java        # Core heap data structure
│               └── app/
│                   └── App.java         # CLI entry point to test heap sort
├── out/                                 # Compiled .class files (after build)
├── .gitignore
├── LICENSE
└── README.md

## How to Compile & Run

### 1. Compile:
(bash)
javac -d out src/main/java/com/**/*.java

### 2. Run:
(bash)
java -cp out com.app.App


## Example Output

og arr:[35, 10, 30, 45, 70, 10, 55, 5, 20, 90, 105]
sorted arr: [5, 10, 10, 20, 30, 35, 45, 55, 70, 90, 105]


## Features

-  Max Heap implementation
-  In-place Heap Sort
-  CLI driver program
-  Modular package structure
-  Plans for:
  - Generic type heap (`Heap<T extends Comparable<T>>`)
  - CLI/GUI separation
  - JUnit testing

---

##  License

This project is licensed under the [MIT License](LICENSE).

---

## Author

**Arvind Chaudhary**  
IT Student & Developer  
[GitHub](https://github.com/arv1nddev)

---

## Contributing

Suggestions, bug reports, and pull requests are welcome!
