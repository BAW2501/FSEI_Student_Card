# Student Card Management System [IHM Module] (Legacy Code - 2021)

This is one of my first Java projects from when I was learning to code. The application manages student ID cards for a university, allowing creation, modification, and printing of student cards.

⚠️ **Note:** This code was written when I was first learning programming. It contains many anti-patterns, lacks proper error handling, and probably has numerous bugs. It's preserved here mainly for historical purposes and to show my programming journey.

## Features
- Create new student cards
- Modify existing student information
- View list of students
- Export cards as PNG files
- Print student cards
- Bilingual support (French/Arabic)

## Project Structure
```{r}
src/
├── Data.java          # Static data and constants
├── DateLabelFormatter.java
├── Interface.java     # Main GUI implementation
├── Main.java
├── Student.java       # Student data model
└── StudentList.java   # Student collection management
```

## Dependencies
- Java Swing for GUI
- JDatePicker 1.3.4 for date selection

## Known Issues
- Hardcoded paths for images
- Mixed language usage in variable names (French/English)
- No input validation
- No proper error handling
- Monolithic GUI class
- Hard-coded student ID generation
- No configuration file support
- No documentation

## Historical Context
This was written in early 2021 as one of my first real Java applications. While it's functional, the code quality reflects my beginner status at the time. I keep it as a reminder of where I started and how much I've learned since then.

## Running the Project
If you really want to run this (at your own risk):
1. Make sure you have Java installed
2. Place required images (pp1.jpg, UMAB_LOGO.png) in the root directory
3. Run Main.java

## License
Feel free to use this code however you want, but I recommend not using it as an example of good practices.