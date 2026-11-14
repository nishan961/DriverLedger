package com.gigpilot.app.ui.report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gigpilot.app.R
import com.gigpilot.app.data.Shift
import com.gigpilot.app.ui.home.HomeViewModel
import com.gigpilot.app.util.Format

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportCardScreen(
    shiftId: Long,
    viewModel: HomeViewModel,
    onBack: () -> Unit
) {
    var shift by remember { mutableStateOf<Shift?>(null) }
    var loaded by remember { mutableStateOf(false) }

    LaunchedEffect(shiftId) {
        shift = viewModel.getShift(shiftId)
        loaded = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.report_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.report_back)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
        ) {
            val current = shift
            if (current == null) {
                if (loaded) {
                    Text(text = stringResource(R.string.report_not_found))
                }
            } else {
                val durationMs = (current.endMs - current.startMs).coerceAtLeast(0)
                val perHour = if (durationMs > 0) {
                    current.earningsCents * 3_600_000L / durationMs
                } else {
                    0L
                }
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(18.dp)) {
                        Text(
                            text = stringResource(R.string.report_duration),
                            style = MaterialTheme.typography.labelLarge
                        )
                        Text(
                            text = Format.duration(durationMs),
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            text = stringResource(R.string.report_earnings),
                            style = MaterialTheme.typography.labelLarge
                        )
                        Text(
                            text = Format.money(current.earningsCents),
                            fontSize = 32.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "${stringResource(R.string.home_net_per_hour)}: " +
                                Format.money(perHour),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            text = "${stringResource(R.string.report_start)}: " +
                                Format.dateTime(current.startMs),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        if (current.endMs > 0) {
                            Text(
                                text = "${stringResource(R.string.report_end)}: " +
                                    Format.dateTime(current.endMs),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(text = stringResource(R.string.report_back), fontSize = 18.sp)
            }
        }
    }
}
