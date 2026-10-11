#include <stdio.h>

int main() {
    int process, resources;
    int count = 0;

    printf("Enter the number of processes and resources:\n");
    scanf("%d %d", &process, &resources);

    int max_requirement[process][resources];
    int allocated[process][resources];
    int rem_process[process][resources];
    int need[process][resources];
    int available[resources];
    int finish[process];
    int safe_sequence[process];

    printf("Enter the maximum resources needed for each process:\n");

    for (int i = 0; i < process; i++) {
        for (int j = 0; j < resources; j++) {
            printf("Enter the maximum requirement for process %d "
                   "and resource %c: ", i + 1, (char)(65 + j));

            scanf("%d", &max_requirement[i][j]);

            available[j] += max_requirement[i][j];
        }
    }

    printf("\nEnter the allocated resources for each process:\n");

    for (int i = 0; i < process; i++) {
        for (int j = 0; j < resources; j++) {
            printf("Enter the allocated resource for process %d "
                   "and resource %c: ", i + 1, (char)(65 + j));

            scanf("%d", &allocated[i][j]);

            rem_process[i][j] = allocated[i][j];

            need[i][j] = max_requirement[i][j] - allocated[i][j];

            available[j] -= allocated[i][j];
        }
    }
    for (int i = 0; i < process; i++) {
        finish[i] = 0;
    }
    printf("\nNeed Matrix:\n");

    for (int i = 0; i < process; i++) {
        for (int j = 0; j < resources; j++) {
            printf("%d ", need[i][j]);
        }
        printf("\n");
    }
    printf("\nAvailable Resources:\n");

    for (int j = 0; j < resources; j++) {
        printf("%c = %d\n", (char)(65 + j), available[j]);
    }

    // Banker's Algorithm
    while (count < process) {
        int found = 0;

        for (int i = 0; i < process; i++) {
            if (finish[i] == 0) {
                int can_execute = 1;

                for (int j = 0; j < resources; j++) {
                    if (need[i][j] > available[j]) {
                        can_execute = 0;
                        break;
                    }
                }

                if (can_execute) {
                    for (int j = 0; j < resources; j++) {
                        available[j] += rem_process[i][j];
                    }

                    safe_sequence[count] = i;
                    count++;

                    finish[i] = 1;
                    found = 1;
                }
            }
        }

        if (found == 0) {
            break;
        }
    }

    // Display result
    if (count == process) {
        printf("\nSystem is in a SAFE state.\n");
        printf("Safe Sequence: ");

        for (int i = 0; i < process; i++) {
            printf("P%d", safe_sequence[i]);

            if (i != process - 1) {
                printf(" -> ");
            }
        }

        printf("\n");
    } else {
        printf("\nSystem is in an UNSAFE state.\n");
        printf("No safe sequence exists for the given allocation.\n");
    }

    return 0;
}