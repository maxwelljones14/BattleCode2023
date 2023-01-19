# paste the run from the queue tab into red_results.txt and blue_results.txt
# start from the actual first match not the header "MPWorking vs Whoever"
# run this script and look at results.csv
# go into spreadsheet and click the cell you want to insert records at
# go to file -> import -> upload -> results.csv -> replace data at current cell
# bam the records are there

import csv
import sys

def get_records(file_name):
    file = open(file_name, 'r')
    lines = file.readlines()
    total = 0
    wins = 0
    records = []
    for line in lines:
        res = ""
        total += 1
        splitted = line.split()
        if splitted[2] != "MPWorking":
            res += "L "
        else:
            res += "W "
            wins += 1
        res += splitted[5]
        records.append(res)
    return records, wins, total

# Pass "r" or "b" to get the records for just that color
if len(sys.argv) > 1:
    if sys.argv[1] == "r":
        red_records, red_wins, red_total = get_records("red_results.txt")
        print(str(red_wins) + "-" + str(red_total - red_wins) + " R")
        print(f"{red_wins / red_total:.3f}")
        exit()
    elif sys.argv[1] == "b":
        blue_records, blue_wins, blue_total = get_records("blue_results.txt")
        print(str(blue_wins) + "-" + str(blue_total - blue_wins) + " B")
        print(f"{blue_wins / blue_total:.3f}")
        exit()

red_records, red_wins, red_total = get_records("red_results.txt")
blue_records, blue_wins, blue_total = get_records("blue_results.txt")

if(red_total != blue_total):
    print("ERROR: red and blue results are not the same length")
    exit()

records = []
for i in range(len(red_records)):
    records.append(red_records[i])
    records.append(blue_records[i])

csvFile = "records.csv"
wins = str(red_wins) + "-" + str(red_total - red_wins) + " R, " + str(blue_wins) + "-" + str(blue_total - blue_wins) + " B"
records = [wins] + records
with open(csvFile, 'w') as f:
    csvwriter = csv.writer(f) 
    csvwriter.writerow(records)
print(wins)
print(f"{(red_wins + blue_wins) / (red_total + blue_total):.3f}")
