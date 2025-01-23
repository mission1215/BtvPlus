package com.skb.bourbon_utils

/**
 * 문자열 길이 및 검사
 * Is empty or null
 *
 * @return
 */
fun String?.isEmptyOrNull(): Boolean = this == null || this.isEmpty()

/**
 * Is blank or null
 *
 * @return
 */
fun String?.isBlankOrNull(): Boolean = this == null || this.isBlank()

/**
 * 문자열 변환
 * Capitalize first
 *
 * @return
 */
fun String.capitalizeFirst(): String = this.replaceFirstChar { it.uppercase() }

fun String.swapCase(): String = this.map {
    if (it.isUpperCase()) it.lowercaseChar() else it.uppercaseChar()
}.joinToString("")

/**
 * 문자열 검색
 * Contains ignore case
 *
 * @param other
 * @return
 */
fun String.containsIgnoreCase(other: String): Boolean = this.contains(other, ignoreCase = true)

/**
 * 문자열 비교
 * Equals ignore case
 *
 * @param other
 * @return
 */
fun String.equalsIgnoreCase(other: String): Boolean = this.equals(other, ignoreCase = true)

/**
 * 문자열 분리
 * Join with
 *
 * @param delimiter
 * @return
 */
fun List<String>.joinWith(delimiter: String): String = this.joinToString(delimiter)

/**
 * 문자열 연결
 * Split and trim
 *
 * @param delimiter
 * @return
 */
fun String.splitAndTrim(delimiter: String): List<String> = this.split(delimiter).map { it.trim() }

/**
 * 부분 문자열 추출
 * Safe substring
 *
 * @param startIndex
 * @param endIndex
 * @return
 */
fun String.safeSubstring(startIndex: Int, endIndex: Int): String {
    if (startIndex < 0 || endIndex > this.length || startIndex > endIndex) return ""
    return this.substring(startIndex, endIndex)
}

/**
 * 문자열 수정
 * Replace ignore case
 *
 * @param oldValue
 * @param newValue
 * @return
 */
fun String.replaceIgnoreCase(oldValue: String, newValue: String): String =
    Regex(oldValue, RegexOption.IGNORE_CASE).replace(this, newValue)

/**
 * 문자열 수정
 * Remove whitespace
 *
 * @return
 */
fun String.removeWhitespace(): String = this.replace("\\s".toRegex(), "")

/**
 * 패턴 매칭 및 정규식
 * Is numeric
 *
 * @return
 */
fun String.isNumeric(): Boolean = this.matches(Regex("\\d+"))

/**
 * Count occurrences
 *
 * @param char
 * @return
 */
fun String.countOccurrences(char: Char): Int = this.count { it == char }
