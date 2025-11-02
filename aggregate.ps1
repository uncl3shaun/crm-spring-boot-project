# Define the output file
$outputFile = "aggregated-sources.txt"

# Clear the file to start fresh
Set-Content -Path $outputFile -Value ""

# Define the directories to scan
$dirsToScan = "src\main\java", "src\main\resources"

foreach ($dir in $dirsToScan) {
    # Get all files recursively (-File ensures we don't get directories)
    Get-ChildItem -Path $dir -Recurse -File | ForEach-Object {
        
        # $_.ToString() gives the relative path like "src\main\java\com\App.java"
        $filePath = $_.ToString()

        # Get the raw content of the file
        $fileContent = Get-Content -Path $_.FullName -Raw

        # Append the header
        Add-Content -Path $outputFile -Value "$filePath`:"

        # Append the content wrapped in quotes.
        Add-Content -Path $outputFile -Value "`"$fileContent`""

        # Append a blank line for separation
        Add-Content -Path $outputFile -Value ""
    }
}

Write-Host "✅ Done! Aggregated sources in $outputFile"