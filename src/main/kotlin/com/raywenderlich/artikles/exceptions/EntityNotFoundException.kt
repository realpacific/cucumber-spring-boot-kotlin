package com.raywenderlich.artikles.exceptions

class EntityNotFoundException(entityName: String) : RuntimeException("$entityName not found.")
