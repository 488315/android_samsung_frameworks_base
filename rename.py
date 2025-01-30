import subprocess
import re
from collections import defaultdict

# List of file patterns to exclude (modify as needed)
EXCLUDED_FILES = ["example1.java", "example2.java"]  # Add filenames to exclude

def find_unique_p0xxx():
    """Uses grep to find unique 'p0XXX' occurrences in Java files and lists affected files."""
    try:
        print("🔍 Searching for occurrences of 'p0XXX' in Java files...\n")

        # Run grep to find occurrences (excluding specific files)
        grep_command = [
            "grep", "-rEHn", r"\.p0[0-9]+", "--include=*.java", "."
        ]
        grep_output = subprocess.run(grep_command, text=True, capture_output=True, check=False)

        if not grep_output.stdout.strip():
            print("✅ No 'p0XXX' occurrences found.")
            return

        # Extract and group occurrences
        file_matches = defaultdict(list)
        unique_p0xxx = set()

        # Parse grep output to collect filenames and matching lines
        for line in grep_output.stdout.split("\n"):
            if not line.strip():
                continue
            match = re.search(r"(\.p0[0-9]+)", line)
            if match:
                p0xxx = match.group(1).strip(".")  # Extract p0XXX without dots
                unique_p0xxx.add(p0xxx)
                file_path, line_number, matched_line = line.split(":", 2)
                if not any(exclude in file_path for exclude in EXCLUDED_FILES):  # Exclude specific files
                    file_matches[p0xxx].append((file_path, line_number, matched_line.strip()))

        # Print unique p0XXX occurrences
        print("\n📌 Unique 'p0XXX' occurrences found:\n")
        for occurrence in sorted(unique_p0xxx):
            print(f"  - {occurrence}")

        # Print filenames grouped under each unique `p0XXX`
        print("\n📂 Files containing each 'p0XXX' (with matched line):\n")
        for p0xxx, matches in file_matches.items():
            print(f"📌 {p0xxx}:")
            for file_path, line_number, matched_line in sorted(matches):
                print(f"   - {file_path} (Line {line_number}): {matched_line}")

    except FileNotFoundError:
        print("❌ Error: 'grep' command not found. Make sure it's installed on your system.")

if __name__ == "__main__":
    find_unique_p0xxx()
