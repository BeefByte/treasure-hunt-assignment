# Dr. Ali
# This script automatically compiles, runs, and compares your results to the provided txt file.
# Make sure to place all the scripts and files within the same directory
# DO NOT MODIFY THE CONTENTS OF THIS FILE!

import subprocess
import sys

# File names for the Java source code
JAVA_SOURCE_FILES = ["TreasureHunt.java", "TreasureHuntDriver.java"]

# The name of the driver class (without .class or .java extension)
DRIVER_CLASS = "TreasureHuntDriver"

# The expected output file
EXPECTED_OUTPUT_FILE = "output.txt"

def compile_java_files():
    """
    Compiles the Java source files.
    """
    compile_command = ["javac"] + JAVA_SOURCE_FILES
    print("Compiling Java files...")
    result = subprocess.run(compile_command, stdout=subprocess.PIPE,
                          stderr=subprocess.PIPE, text=True)
    if result.returncode != 0:
        print("Compilation failed:")
        print(result.stderr)
        sys.exit(1)
    else:
        print("Compilation successful.\n")

def run_driver():
    """
    Runs the compiled Java driver program and returns its output.
    """
    run_command = ["java", DRIVER_CLASS]
    print("Running test cases...\n")
    result = subprocess.run(run_command, stdout=subprocess.PIPE,
                          stderr=subprocess.PIPE, text=True)
    if result.returncode != 0:
        print("Execution failed:")
        print(result.stderr)
        sys.exit(1)
    else:
        return result.stdout

def read_expected_output():
    """
    Reads the expected output from the output.txt file.
    """
    try:
        with open(EXPECTED_OUTPUT_FILE, 'r') as f:
            return f.read()
    except FileNotFoundError:
        print(f"Expected output file '{EXPECTED_OUTPUT_FILE}' not found!")
        sys.exit(1)

def compare_output(actual_output, expected_output):
    """
    Compares the actual output from the driver with the expected output.
    """
    if actual_output.strip() == expected_output.strip():
        print("Output matches the expected output!")
    else:
        print("Output does not match the expected output!")
        print("\n--- Expected Output ---\n")
        print(expected_output)
        print("\n--- Actual Output ---\n")
        print(actual_output)

if __name__ == "__main__":
    compile_java_files()
    actual_output = run_driver()
    print("Driver Output:\n")
    print(actual_output)
    expected_output = read_expected_output()
    compare_output(actual_output, expected_output)
