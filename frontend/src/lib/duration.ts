export const getSongLength = (durationMs: number) => {
  const totalSeconds = durationMs / 1000;
  const allMinutes = totalSeconds / 60;
  const hours = (allMinutes / 60).toFixed(0);
  const minutesNumber = allMinutes % 60;
  const minutes =
    minutesNumber < 10
      ? "0" + minutesNumber.toFixed(0)
      : minutesNumber.toFixed(0);
  const secondsNumber = +(totalSeconds % 60).toFixed(0);
  const seconds = secondsNumber < 10 ? "0" + secondsNumber : secondsNumber;
  return `${+hours ? hours + "h:" : ""}${minutes}m:${seconds}s`;
};
