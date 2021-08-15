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
            val filteredInput = input.filter { it.isDigit() }.toMutableList()
            val patternMutList = pattern.toMutableList()
            val result = mutableListOf<String>()
            while (filteredInput.isNotEmpty()) {
                val patternCurrentChar = patternMutList.removeFirstOrNull() ?: "#"
                if (patternCurrentChar.toString() == "#") {
                    result.add(filteredInput.removeFirst().toString())
                } else {
                    result.add(patternCurrentChar.toString())
                }
            }
            return result.joinToString("")
        }
    }
}