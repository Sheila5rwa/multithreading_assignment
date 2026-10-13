# Day 21 - Multithreading Assignment

Bank transfer simulation: 10 accounts x 10,000 = 100,000 total, 100,000 random transfers on a 10-thread pool.

## Team
| Member | Branch | Package | Phases |
|---|---|---|---|
| Larissa Iriza | `larissa-runnable` | `phase1_race`, `phase2_sync` | 1-3: Runnable, race condition, synchronized + timing |
| Rwakunda Kaliza Sheila Milena | `sheila-callable` | `phase3_callable` | 4-5: Callable, Futures, success/failure counts |

## How to run (from the `src` folder)
```bash
javac phase3_callable/*.java
java phase3_callable.Main
```

## Results
| Phase | Final total | Time (ms) | Notes |
|---|---|---|---|
| Phase 1 - no sync | _fill in_ | _fill in_ | total != 100,000 proves the race condition |
| Phase 2 - synchronized | _fill in_ | _fill in_ | always exactly 100,000 |
| Phase 3 - Callable + Futures | _fill in_ | _fill in_ | successes: _ / failures: _ |