import { Input } from "@/components/ui/input";
import Image from "next/image";
import { HTMLAttributes, HTMLProps } from "react";

export default function HomePage() {
  return (
    <section className="border w-full h-full flex flex-col bg-slate-100">
      <Input
        className="w-96 self-center mt-4 bg-white"
        placeholder="Search by artist, song, etc..."
      />
      <main className="w-full max-w-full box-border *:box-border">
        <MainSongsContainer title={"My top artists"} />
        <h1 className="p-4 text-[2rem]">Search Results</h1>
        <MainSongsContainer />
      </main>
    </section>
  );
}

interface MainSongsContainerProps {
  title?: String;
}
function MainSongsContainer({
  title,
  className,
}: MainSongsContainerProps & HTMLAttributes<HTMLDivElement>) {
  return (
    <section
      className={`*:w-full flex flex-col px-8 w-full gap-4 max-h-96 overflow-hidden ${className}`}
    >
      <h1 className=" text-slate-600">{title}</h1>
      <div className="grid border grid-cols-auto-fit-250 *:border  gap-4 p-8 rounded-lg overflow-auto">
        {Array.from(Array(50).keys()).map((el) => {
          return (
            <div
              className="relative max-w-64 min-w-64 flex shadow-sm bg-slate-50 rounded-md overflow-hidden min-h-24 max-h-24 last:justify-self-start"
              key={el}
            >
              <Image
                className="!relative object-cover aspect-square min-w-24 max-w-24 overflow-hidden"
                src="/gunsnroses.jpg"
                alt="Artist Image"
                fill
              />
              <div className="w-fit px-4">
                <h2>Artist name</h2>
                <p>Genres</p>
              </div>
            </div>
          );
        })}
      </div>
    </section>
  );
}
