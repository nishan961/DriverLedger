package com.gigpilot.app.util

import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.math.acos
import kotlin.math.asin
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.sin
import kotlin.math.tan

/**
 * Sunrise / sunset calculator (NOAA algorithm) so the app can switch between the
 * white daytime UI and the true-black night UI by the sun, never by a fixed clock.
 * Default coordinates are Metro Vancouver, the launch market.
 */
object Solar {

    const val VANCOUVER_LAT = 49.2827
    const val VANCOUVER_LNG = -123.1207

    fun isDaytime(
        now: ZonedDateTime,
        lat: Double = VANCOUVER_LAT,
        lng: Double = VANCOUVER_LNG
    ): Boolean {
        val (sunrise, sunset) = sunriseSunset(now.toLocalDate(), lat, lng, now.zone)
        val t = now.toLocalTime()
        return !t.isBefore(sunrise) && t.isBefore(sunset)
    }

    fun sunriseSunset(
        date: LocalDate,
        lat: Double,
        lng: Double,
        zone: ZoneId
    ): Pair<LocalTime, LocalTime> =
        calc(date, lat, lng, zone, isSunrise = true) to
            calc(date, lat, lng, zone, isSunrise = false)

    private fun calc(
        date: LocalDate,
        lat: Double,
        lng: Double,
        zone: ZoneId,
        isSunrise: Boolean
    ): LocalTime {
        val n = date.dayOfYear.toDouble()
        val lngHour = lng / 15.0
        val t = if (isSunrise) n + (6 - lngHour) / 24.0 else n + (18 - lngHour) / 24.0
        val meanAnomaly = 0.9856 * t - 3.289
        var trueLongitude = meanAnomaly +
            1.916 * sin(rad(meanAnomaly)) +
            0.020 * sin(rad(2 * meanAnomaly)) +
            282.634
        trueLongitude = mod360(trueLongitude)
        var rightAscension = Math.toDegrees(atan(0.91764 * tan(rad(trueLongitude))))
        rightAscension = mod360(rightAscension)
        rightAscension += floor(trueLongitude / 90.0) * 90.0 - floor(rightAscension / 90.0) * 90.0
        rightAscension /= 15.0
        val sinDec = 0.39782 * sin(rad(trueLongitude))
        val cosDec = cos(asin(sinDec))
        val zenith = 90.833
        val cosH = (cos(rad(zenith)) - sinDec * sin(rad(lat))) / (cosDec * cos(rad(lat)))
        if (cosH > 1.0 || cosH < -1.0) {
            // Sun does not rise/set this day at this latitude — sane fallback.
            return if (isSunrise) LocalTime.of(7, 0) else LocalTime.of(19, 0)
        }
        var hourAngle = if (isSunrise) {
            360.0 - Math.toDegrees(acos(cosH))
        } else {
            Math.toDegrees(acos(cosH))
        }
        hourAngle /= 15.0
        val meanTime = hourAngle + rightAscension - 0.06571 * t - 6.622
        var utc = (meanTime - lngHour) % 24.0
        if (utc < 0) utc += 24.0
        val offsetHours = zone.rules.getOffset(date.atTime(12, 0)).totalSeconds / 3600.0
        var local = (utc + offsetHours) % 24.0
        if (local < 0) local += 24.0
        val hour = floor(local).toInt()
        val minute = floor((local - hour) * 60.0).toInt()
        return LocalTime.of(hour % 24, minute % 60)
    }

    private fun rad(degrees: Double): Double = Math.toRadians(degrees)

    private fun mod360(value: Double): Double {
        var result = value % 360.0
        if (result < 0) result += 360.0
        return result
    }
}
