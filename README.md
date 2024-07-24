# RV32I Single Cycle Processor

This repository contains the implementation of a single-cycle RV32I processor in Chisel, a hardware construction language embedded in Scala. The processor executes RISC-V RV32I instructions and includes modules such as ALU, Branch, Control, Data Memory, Instruction Memory, PC, Register File, and Immediate Generator (ImmGen).

## Table of Contents
- [Overview](#overview)
- [Modules](#modules)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Testing](#testing)
- [Dependencies](#dependencies)
- [License](#license)

## Overview
This project implements a single-cycle RV32I processor. The processor is designed to fetch, decode, and execute instructions in a single clock cycle. It supports basic ALU operations, memory access, and control flow instructions.

## Modules
- **ALU**: Performs arithmetic and logic operations.
- **Branch**: Handles branch operations.
- **Control**: Generates control signals based on the current instruction.
- **Data Memory**: Represents the data memory.
- **Instruction Memory**: Stores the program instructions.
- **Immediate Generator (ImmGen)**: Generates immediate values for instructions.
- **PC (Program Counter)**: Holds the address of the current instruction.
- **Register File**: Stores the registers.

## Getting Started
### Prerequisites
- Scala
- SBT (Scala Build Tool)
- Chisel
- ChiselTest

### Installation
1. **Clone the repository**:
    ```bash
    git clone https://github.com/your-username/single_cycle.git
    cd single_cycle
    ```

2. **Install dependencies**:
    Ensure you have Scala and SBT installed. Then, run:
    ```bash
    sbt update
    ```

## Usage
### Running the Processor
To run the RV32I processor, execute:
```bash
sbt run
```

### Simulating with Test Bench
A basic test bench is provided to simulate the processor. To run the tests:
```bash
sbt test
```

## Testing
The provided test suite (`RV32Itest`) runs a basic simulation of the processor to ensure correct functionality. The test bench initializes the processor, steps the clock, and checks the output.


## Test Cases
### Program 1
```assembly
addi x5 x0 0
addi x6 x0 5
add x8 x6 x5
LOOP:
addi x5 x5 1
sw x5 100(x0)
beq x5 x6 ANS
jal LOOP
ANS: lw x7 100(x0)
```

### Program 2
```assembly
addi x5 x0 3
LOOP:
addi x5 x5 1
addi x6 x0 7
sw x6 100(x5)
lw x7 100(x5)
bne x5 x7 LOOP
```

### Program 3
```assembly
addi x5 x0 0
addi x7 x0 1
addi x6 x0 10
addi x28 x0 0
LOOP: beq x28 x6 END
add x29 x5 x7
add x5 x0 x7
add x7 x0 x29
jal LOOP
END:
```

### Fibonacci Series
```assembly
addi x1, x0, 0
addi x2, x0, 1
addi x10, x0, 4
addi x6, x0, 40
addi x3, x0, 0
addi x4, x3, 4
sw x1, 0x100(x3)
sw x2, 0x100(x4)
addi x14, x0, 8
addi x5, x0, 8
addi x13, x0, 8
addi x15, x0, 4
addi x9, x0, 4
add x8, x1, x2
up:
beq x5, x6, end
add x12, x0, x8
sw x12, 0x100(x5)
lw x11, 0x100(x9)
add x8, x11, x8
addi x5, x5, 4
addi x9, x9, 4
jal x7, up
end:
beq x3, x6, break
lw x16, 0x100(x3)
addi x3, x3, 4
jal x7, end
break:
```

### Running the Assembly Programs
1. **Write the assembly code in Venus (RISC-V Simulator)**:
    - Open [Venus](https://www.kvakil.me/venus/).
    - Write or paste your assembly code in the editor.
    - Assemble the code to ensure there are no syntax errors.
    
2. **Dump the machine code**:
    - After assembling the code, dump the machine code to a file.
    - Copy the machine code and paste it into a text file.

## Dependencies
- **Chisel**: Hardware construction language.
- **ChiselTest**: Testing framework for Chisel.
