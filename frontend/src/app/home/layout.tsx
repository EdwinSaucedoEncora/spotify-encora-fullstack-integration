import {
  IconArrowsShuffle,
  IconPlayerPlay,
  IconPlayerTrackNext,
  IconPlayerTrackPrev,
  IconRepeat,
} from "@tabler/icons-react";

export default function HomeLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <>
      {children}
      <section className="fixed h-16 w-full bottom-0 z-10 bg-slate-800 grid place-items-center p-2">
        <div className="flex  gap-4 *:stroke-slate-100 *:size-6 items-center justify-center *:cursor-pointer *:hover:*:opacity-65">
          <IconArrowsShuffle />
          <IconPlayerTrackPrev />
          <IconPlayerPlay className="!size-10" />
          <IconPlayerTrackNext />
          <IconRepeat />
        </div>
      </section>
    </>
  );
}
