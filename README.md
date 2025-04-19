# Shortest Paths Algorithms

## 📌 Overview

This Java project implements three well-known shortest paths algorithms for directed, weighted graphs:

- **Dijkstra's Algorithm**
- **Bellman-Ford Algorithm**
- **Floyd-Warshall Algorithm**

The system includes a command-line interface to interact with the graph, run shortest path queries, and detect negative cycles. It also features unit tests to validate the correctness and compare the performance of the algorithms.

---

## 📁 Project Structure

```
ShortestPaths/
│
├── src/
│   ├── Graph.java              # Graph class and algorithm implementations
│   ├── Main.java               # Command line interface
│   └── ...
│
├── test/
│   └── GraphTest.java          # 15–20 JUnit tests
│
├── graphs/
│   └── sample_input.txt        # Sample graph input files
│
├── report/
│   └── AnalysisReport.pdf      # Time and space analysis and comparison
│
└── README.md                   # This file
```

---

## 📄 Input Graph Format

Each input file must follow this format:

```
V E
i1 j1 w1
i2 j2 w2
...
iE jE wE
```

Where:

- `V`: Number of vertices
- `E`: Number of edges
- Each line `i j w` defines a directed edge from node `i` to `j` with weight `w` (can be negative)

---

## ✅ Implemented Features

### 🧠 Algorithms

- **`dijkstra(int source, int[] costs, int[] parents)`**
- **`bellmanFord(int source, int[] costs, int[] parents)`**
- **`floydWarshall(int[][] costs, int[][] predecessors)`**

### 🖥️ Command Line Interface

- Load graph from a file
- Single-source shortest paths:
  - Choose source and algorithm
  - Query cost/path to a node
- All-pairs shortest paths:
  - Choose algorithm
  - Query cost/path between any two nodes
- Check for negative cycles using Bellman-Ford or Floyd-Warshall

### 🧪 Testing

- 15–20 JUnit tests covering:
  - Correctness of results
  - Handling of negative edges and cycles
  - Performance comparison
