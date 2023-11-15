#ifndef clox_chunk_h
#define clox_chunk_h

#include "common.h"

typedef enum {
    OP_RETURN,
} OpCode;

typedef struct {
    // In addition to arr itself, we keep the number of elems in the array we have allocated (capacity) and how many of those entries are in use (count).
    int count;
    int capacity;

    uint8_t* code;
} Chunk;

void initChunk(Chunk* Chunk);
void writeChunk(Chunk* Chunk, uint8_t byte);

#endif
