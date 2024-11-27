# Schilda Disaster Management Planning Tool
**Wintersemester 2024/2025**  
**Prof. Dr. Barbara Sprick**  
**Project Start Date: November 11, 2024**



## Project Overview
The **Schilda Disaster Management Planning Tool** is designed to assist the city of Schilda in effectively managing disaster situations. As Schilda transitions from a small town to a large city, disaster preparedness has become critical. This tool provides flexible, efficient solutions for handling disasters by enabling functionalities like city map management, route planning, communication infrastructure rebuilding, and emergency service deployment.

---

## Functionalities

### 1. **City Map Management**
- **B1: Load and Display City Map**  
  Reads and outputs a city map represented as an adjacency matrix.

- **B2: Modify City Map**  
  Allows for marking road closures and damage locations. Users can add points of interest like supply points and deployment sites.
    - *Optional:* Support for distinguishing between land routes and waterways for hybrid routing.

---

### 2. **Disaster Planning Functions**

- **F1: Rebuild Communication Infrastructure**  
  Calculates a basic network to ensure reliable communication between key nodes like rescue stations and hospitals, considering minimal repair costs.  
  **Input:** Infrastructure nodes with repair costs for surrounding connections.

- **F2: Evacuation Route Planning**  
  Determines if the current infrastructure is sufficient for evacuation or if additional resources are needed.  
  **Input:** List of assembly points, emergency shelters, and updated city map capacities.
    - *Optional:* Consider routes accessible only by boat.

- **F3: Emergency Services Route Planning**  
  Calculates the fastest routes to deployment sites, accounting for road conditions.
    - *Optional:* Hybrid routes involving both car and boat access.

- **F4: Supply Points for Emergency Services**  
  Identifies optimal locations for additional supply points to minimize the average distance to relief forces.  
  **Input:** City map with deployment locations and potential waterways.

- **F5: Deployment Planning for Emergency Services**  
  Distributes emergency personnel and resources efficiently across various deployment sites, taking specific skills and equipment into account.
    - *Optional:* Different scenarios for supply prioritization based on equipment availability and urgency.

---

## Development Tasks

### 1. **Graph Data Structure**
- Represent and manipulate the city map as an adjacency matrix.

### 2. **Graph Algorithms**
- Implement algorithms for each functionality based on test data from adjacency matrices.

### 3. **Problem Modeling**
- Model disaster management scenarios using graph theory and appropriate data structures.

---

## Technologies

- **Programming Language:** Java
- **Build Tool:** Maven

### How to Compile and Run the Project

1. **Clone the Repository:**
```bash
git clone https://github.com/ZuebeyirEser/schilda-disaster-management-tool.git
cd schilda-disaster-management-tool
```
2. **Compile the Project Using Maven:**
```bash
mvn clean compile
```
3**Run the Application**
```bash
mvn exec:java -Dexec.mainClass="de.thab.algo.App"
```
## How to Contribute

We welcome contributions from all team members! Please follow these guidelines when contributing:

### 1. Fork the Repository

Go to the project repository on GitHub and click on Fork.

### 2. Clone Your Fork

Clone your forked repository to your local machine:

```bash
git clone <your-forked-repository-url>
cd FinalProject-Schilda-Disaster-Management-Tool
```

### 3. Create a New Branch

Create a branch for your feature or bug fix:
```bash
git checkout -b feature/your-feature-name
```
### 4. Make Changes

Implement your changes.
### 5. Commit Your Changes

Stage and commit your changes: 
```bash
git add .
git commit -m "Add your detailed commit message here"
```
### 6. Push Your Branch

Push your branch to your forked repository:
```bash
git push origin feature/your-feature-name
```
### 7. Create a Pull Request

1. Go to the forked repository and click on **Sync fork**.
2. Please remember to update your local repository if the fork is behind the main branch
```bash
git pull
```
3. Click on **New Pull Request** and select your branch.
4. Add a clear description of the changes you've made and submit the pull request.

