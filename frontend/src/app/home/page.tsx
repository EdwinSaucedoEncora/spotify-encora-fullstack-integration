"use client";
import { Input } from "@/components/ui/input";
import {
  Tooltip,
  TooltipContent,
  TooltipProvider,
  TooltipTrigger,
} from "@/components/ui/tooltip";

import useDebounce from "@/lib/debounce";
import { useGetTopUserArtistsQuery, useSearchQuery } from "@/services/spotify";
import { ToggleGroup, ToggleGroupItem } from "@radix-ui/react-toggle-group";
import Image from "next/image";
import Link from "next/link";
import { HTMLAttributes, useState } from "react";

export default function HomePage() {
  const session = localStorage.getItem("session");
  const [search, setSearch] = useState("");
  const debouncedSearch = useDebounce(search, 500);
  const {
    data: userTopArtists,
    error,
    isLoading,
  } = useGetTopUserArtistsQuery(undefined, {
    skip: !Boolean(session),
  });
  const {
    data,
    isLoading: isLoadingSearch,
    isError: isErrorSearch,
  } = useSearchQuery(debouncedSearch);

  const [toggle, setToggle] = useState("albums");

  return (
    <section className="border w-full h-full flex flex-col bg-slate-100 items-center">
      <div>
        <div className="relative">
          <Input
            className="w-96 self-center mt-4 bg-white"
            placeholder="Search by artist, song, etc..."
            onChange={(e) => setSearch(e.target.value)}
          />
          {!isLoadingSearch && !isErrorSearch && (
            <div className="w-96 h-96 absolute bg-white left-0 z-10 overflow-auto p-4 ">
              <ToggleGroup
                type="single"
                className="*:border *:p-2 *:rounded-3xl flex justify-center gap-2 py-2 *:[*]:border-4"
                defaultValue={toggle}
                onValueChange={(value) => setToggle(value)}
              >
                <ToggleGroupItem value="albums" aria-label="Toggle albums">
                  Albums
                </ToggleGroupItem>
                <ToggleGroupItem value="artists" aria-label="Toggle artists">
                  Artists
                </ToggleGroupItem>
                <ToggleGroupItem value="tracks" aria-label="Toggle tracks">
                  Tracks
                </ToggleGroupItem>
              </ToggleGroup>
              <div className="flex flex-col">
                {data?.[toggle.toLowerCase()]?.items?.map(
                  ({ id, name, images }) => {
                    const [image] = images ?? [];
                    return (
                      <Link href={`${toggle}/${id}`} key={id} className="p-2">
                        {name}
                      </Link>
                    );
                  }
                )}
              </div>
            </div>
          )}
        </div>
      </div>
      <main className="w-full max-w-full h-full">
        {!isLoading && (
          <TopArtistContainer
            title={"My top artists"}
            items={userTopArtists?.items}
          />
        )}
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
  return (
    <section
      className={`*:w-full flex flex-col px-8 w-full gap-4 overflow-hidden ${className}`}
    >
      <h1 className=" text-slate-600">{title}</h1>
      <div className="grid border grid-cols-auto-fit-250 *:border  gap-4 p-8 rounded-lg overflow-auto">
        {items?.map(({ id, name, genres, images }, el) => {
          return (
            <Link href={`/artists/${id}`} key={id ?? el}>
              <TooltipProvider>
                <Tooltip>
                  <TooltipTrigger>
                    <div className="relative max-w-64 min-w-64 flex shadow-sm bg-slate-50 rounded-md overflow-hidden min-h-24 max-h-24 last:justify-self-start">
                      <Image
                        className="!relative object-cover aspect-square min-w-24 max-w-24 overflow-hidden"
                        src={images?.at(0)?.url}
                        alt={name + "_image"}
                        fill
                      />
                      <div className="w-fit px-4 text-left ">
                        <h2 className="text-base leading-none py-1">{name}</h2>
                        <p className="text-xs"> {genres?.join(" ")}</p>
                      </div>
                    </div>
                  </TooltipTrigger>
                  <TooltipContent>
                    <p className="max-w-32">{`${genres}`}</p>
                  </TooltipContent>
                </Tooltip>
              </TooltipProvider>
            </Link>
          );
        })}
      </div>
    </section>
  );
}
