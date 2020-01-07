# 3444-Project
### Malena Reyes
### Arnulfo Moses Molina
### Matthew Harris
-------------------------------------------------------------------------------
## Overview
This is a basic android audio visualizer based on a group project for a software engineering course at the University of North Texas.

## Visualization modes
Currently there are two visualization modes: piano and fft. Both use fft, so the titles are a bit misleading.

### Piano Mode
This mode uses the frequency bins from the android SDK visualizer that match a piano key's frequency the closest. Some of the bins from 1-14 are not represented by any freuency bin because the android sdk visualizer does not have that level of resolution.

These keys are represented as bars with changing heights based on the magnitude of the frequency.

### FFT Mode
This mode uses all frequency bins available from the android SDK visualizer. Each bin is represented as same-height bars with changing alpha channels based on the magnitude of the frequency.
