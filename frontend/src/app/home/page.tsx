"use client";
import { Input } from "@/components/ui/input";
import { useGetTopUserArtistsQuery, useSearchQuery } from "@/services/spotify";
import Image from "next/image";
import Link from "next/link";
import { HTMLAttributes } from "react";

export default function HomePage() {
  const {
    data: userTopArtists,
    error,
    isLoading,
  } = useGetTopUserArtistsQuery(undefined);
  const { data } = useSearchQuery("a");
  return (
    <section className="border w-full h-full flex flex-col bg-slate-100">
      <Input
        className="w-96 self-center mt-4 bg-white"
        placeholder="Search by artist, song, etc..."
      />
      <main className="w-full max-w-full box-border *:box-border">
        {!isLoading && (
          <TopArtistContainer
            title={"My top artists"}
            items={userTopArtists?.items}
          />
        )}
        <h1 className="p-4 text-[2rem]">Search Results</h1>
      </main>
    </section>
  );
}

interface TopArtistContainerProps {
  title?: String;
  items?: Array<any>;
}

function TopArtistContainer({
  title,
  items,
  className,
}: TopArtistContainerProps & HTMLAttributes<HTMLDivElement>) {
  console.log({ items });
  return (
    <section
      className={`*:w-full flex flex-col px-8 w-full gap-4 max-h-96 overflow-hidden ${className}`}
    >
      <h1 className=" text-slate-600">{title}</h1>
      <div className="grid border grid-cols-auto-fit-250 *:border  gap-4 p-8 rounded-lg overflow-auto">
        {items?.map(({ id, name, genres, images }, el) => {
          return (
            <Link href={`/artist/${id}`} key={id ?? el}>
              <div className="relative max-w-64 min-w-64 flex shadow-sm bg-slate-50 rounded-md overflow-hidden min-h-24 max-h-24 last:justify-self-start">
                <Image
                  className="!relative object-cover aspect-square min-w-24 max-w-24 overflow-hidden"
                  src={images?.at(0)?.url}
                  alt="Artist Image"
                  fill
                />
                <div className="w-fit px-4">
                  <h2>{name}</h2>
                  <p className="text-xs"> {genres?.join(" ")}</p>
                </div>
              </div>
            </Link>
          );
        })}
      </div>
    </section>
  );
}
