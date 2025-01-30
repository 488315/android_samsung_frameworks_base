import os
import subprocess
import threading
import concurrent.futures
from pathlib import Path
from tqdm import tqdm  # Sleek progress bar

# Number of threads to use (default: CPU count)
THREADS = os.cpu_count() or 4

# Lock for thread-safe console updates
progress_lock = threading.Lock()

def find_java_files(root_dir):
    """Recursively find all .java files in the given directory."""
    return list(Path(root_dir).rglob("*.java"))

def format_java_file(java_file):
    """Formats a single Java file using google-java-format."""
    try:
        subprocess.run(
            ["google-java-format", "--replace", str(java_file)],
            check=True, stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL
        )
        return True  # Success
    except subprocess.CalledProcessError:
        return False  # Failure

def main(root_dir):
    """Formats all Java files in a directory using multithreading, Ninja style."""
    java_files = find_java_files(root_dir)
    total_files = len(java_files)

    if total_files == 0:
        print("No Java files found.")
        return

    print(f"🔧 Formatting {total_files} Java files... (Silent Mode)")

    failed_files = []
    with tqdm(total=total_files, desc="🔄 Formatting", unit="file", dynamic_ncols=True) as pbar:
        with concurrent.futures.ThreadPoolExecutor(max_workers=THREADS) as executor:
            future_to_file = {executor.submit(format_java_file, file): file for file in java_files}

            for future in concurrent.futures.as_completed(future_to_file):
                java_file = future_to_file[future]
                success = future.result()
                with progress_lock:
                    pbar.update(1)
                    if not success:
                        failed_files.append(java_file)

    print("\n✅ Formatting complete." if not failed_files else "\n⚠️ Formatting completed with errors.")

    # Show failed files only if any exist
    if failed_files:
        print("\n🚨 Failed to format the following files:")
        for file in failed_files:
            print(f"  ❌ {file}")

if __name__ == "__main__":
    import sys
    if len(sys.argv) != 2:
        print("Usage: python format_java.py <path_to_java_source>")
    else:
        main(sys.argv[1])
