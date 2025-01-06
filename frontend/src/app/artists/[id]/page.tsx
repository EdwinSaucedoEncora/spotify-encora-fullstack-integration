"use client";

import { Button } from "@/components/ui/button";
import { useGetArtistQuery } from "@/services/spotify";
import Image from "next/image";
import { useParams, usePathname, useRouter } from "next/navigation";
import { Suspense } from "react";
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import Link from "next/link";
import {
  Tooltip,
  TooltipContent,
  TooltipProvider,
  TooltipTrigger,
} from "@/components/ui/tooltip";
import { getSongLength } from "@/lib/duration";
import { ArtistDescription } from "@/components/ui/artist";

function Artist() {
  const { id } = useParams();
  const { data, isLoading, isError } = useGetArtistQuery(id);
  const { popularSongs, info, discography } = data ?? {};
  const { name, images, id: artistId, genres, popularity, type } = info ?? {};
  const image = images?.at(0);
  const router = useRouter();
  return (
    <main className="p-4 flex flex-col items-center">
      <Button
        className="self-start"
        type="button"
        onClick={() => router.back()}
      >
        Go back
      </Button>
      {!isLoading && !isError && (
        <>
          <div className="flex items-start p-4">
            <Image
              className="size-64 rounded-lg"
              width={image.width}
              height={image.height}
              src={image.url}
              alt={name + "_img"}
            />

            <ArtistDescription
              id={artistId}
              genres={genres}
              total={discography.total}
              name={name}
              popularity={popularity}
              type={type}
            />
          </div>
          <div className="w-[64rem] flex items-center flex-col">
            <h2 className="w-full">Popular Songs</h2>
            <Table className="w-full mx-auto">
              <TableCaption>{name} popular songs list.</TableCaption>
              <TableHeader>
                <TableRow>
                  <TableHead className="w-[100px]">#</TableHead>
                  <TableHead>Image</TableHead>
                  <TableHead>Song Name</TableHead>
                  <TableHead>Album</TableHead>
                  <TableHead className="text-right">Song Length</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {popularSongs?.tracks.map(
                  ({ id, name, album, duration_ms }, index) => {
                    const {
                      images: [image],
                      name: albumName,
                    } = album;
                    return (
                      <TableRow key={id}>
                        <TableCell className="font-medium">
                          {index + 1}
                        </TableCell>
                        <TableCell>
                          <Image
                            className="size-8"
                            width={image.width}
                            height={image.height}
                            src={image.url}
                            alt={name + "_album-cover"}
                          />
                        </TableCell>
                        <TableCell>{name}</TableCell>
                        <TableCell>
                          <Link href={`/albums/${album.id}`}>{albumName}</Link>
                        </TableCell>
                        <TableCell className="text-right">
                          {getSongLength(duration_ms)}
                        </TableCell>
                      </TableRow>
                    );
                  }
                )}
              </TableBody>
            </Table>
          </div>
          <div className="flex gap-4 flex-wrap justify-center">
            <h2 className="w-full font-medium">Discography</h2>
            {discography.items.map(({ id, images: [image], name }, index) => (
              <Link href={`/albums/${id}`} key={id}>
                <TooltipProvider>
                  <Tooltip>
                    <TooltipTrigger>
                      <Image
                        className="size-16"
                        width={image.width}
                        height={image.height}
                        src={image.url}
                        alt={name + "_album-cover"}
                      />
                      <p className="max-w-16 text-center font-light text-sm max-h-[5ch] overflow-hidden">
                        {name}
                      </p>
                    </TooltipTrigger>
                    <TooltipContent>
                      <p>{name}</p>
                    </TooltipContent>
                  </Tooltip>
                </TooltipProvider>
              </Link>
            ))}
          </div>
        </>
      )}
    </main>
  );
}

export default function ArtistPage() {
  return (
    <Suspense fallback={<div>Loading...</div>}>
      <Artist />
    </Suspense>
  );
}
