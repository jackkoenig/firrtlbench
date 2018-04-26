#!/usr/bin/env python3

import subprocess
import re
from statistics import median, stdev

def extract_max_size(output):
    m = re.search('(\d+)\s+maximum resident set size', output, re.MULTILINE)
    if m :
        return int(m.group(1))
    else :
        raise Exception('Max set size not found!')

def run_firrtl(design):
    cmd = ['/usr/bin/time','-l','firrtl/utils/bin/firrtl','-i',design,'-o','out.v','-X','verilog']
    result = subprocess.run(cmd, stderr=subprocess.PIPE)
    size = extract_max_size(result.stderr.decode('utf-8'))
    print(size)
    return size

N = 10
designs = ["firrtl/regress/Rob.fir", "regress/freechips.rocketchip.system.DualCoreConfig.fir"]

results = {}

for design in designs:
    print('Running {}...'.format(design))
    times = [run_firrtl(design) / 1000000.0 for i in range(N)]
    results[design] = [median(times), stdev(times)]

for design, res in results.items():
    print(design)
    print('    median: {} MB'.format(res[0]))
    print('    stdev: {} MB'.format(res[1]))
