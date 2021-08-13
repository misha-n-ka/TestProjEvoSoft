package com.testprojevosoft.utils

class PhoneNumberFormatter {

    companion object {
        /*this function needs to format phone number as pattern
        * you must use '#' in pattern to indicate a digit from input
        * Example:
        * input = 0000000000
        * pattern = (###)###-##-##
        * result = (000)000-00-00*/
        fun format(input: String, pattern: String): String {
            val filteredInput = input.filter { it.isDigit() }
            val inputMutList = filteredInput.split("")
                .subList(1, input.length + 1)
                .toMutableList()
            val resultList = mutableListOf<String>()
            for (i in pattern.indices) {
                if (pattern[i] == '#') {
                    if (inputMutList.isEmpty()) continue
                    val currentDigit = inputMutList.removeAt(0)
                    resultList.add(currentDigit)
                } else {
                    resultList.add(pattern[i].toString())
                }
            }
            return resultList.joinToString("")
        }
    }
}